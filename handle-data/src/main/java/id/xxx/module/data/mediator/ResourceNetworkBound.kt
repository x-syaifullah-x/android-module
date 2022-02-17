package id.xxx.module.data.mediator

import id.xxx.module.model.sealed.Result
import id.xxx.module.data.mediator.base.AbstractResource
import id.xxx.module.data.mediator.exception.ResourceNetworkBoundError
import id.xxx.module.model.sealed.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class ResourceNetworkBound<ResultType, RequestType> private constructor(
    private val loadMode: LoadMode,
    private val blockResult: suspend () -> Flow<ResultType?>?,
    private val blockOnResult: suspend (ResultType) -> Boolean,
    private val blockShouldFetch: (ResultType?) -> Boolean,
    private val blockFetch: suspend () -> Flow<Result<RequestType>>?,
    private val blockOnFetch: suspend (Result<RequestType>, ResultType?) -> Unit,
) : AbstractResource<ResultType, RequestType>() {

    companion object {
        fun <ResultType, RequestType> create(
            /**
             * if [loadMode] is [LoadMode.NETWORK_FIRST], [blockFetch] called first
             * if [loadMode] is [LoadMode.LOCAL_FIRST], [blockResult] called first
             */
            loadMode: LoadMode = LoadMode.NETWORK_FIRST,
            /**
             * first called [loadMode] is [LoadMode.LOCAL_FIRST]
             */
            blockResult: suspend () -> Flow<ResultType?>?,
            /**
             * @if [blockOnResult] is false [blockResult] always [Resource.Empty]
             */
            blockOnResult: suspend (ResultType?) -> Boolean = { true },
            /**
             * [loadMode] is [LoadMode.NETWORK_FIRST] [blockShouldFetch] skip
             * [blockShouldFetch] is true  && [loadMode] is [LoadMode.LOCAL_FIRST] [fetch] always called
             * [blockShouldFetch] is false  && [loadMode] is [LoadMode.LOCAL_FIRST] [fetch] never called
             */
            blockShouldFetch: (ResultType?) -> Boolean = { true },
            /**
             * called if [loadMode] is [LoadMode.NETWORK_FIRST] || [blockShouldFetch] is true
             */
            blockFetch: suspend () -> Flow<Result<RequestType>>? = { null },
            /**
             * called after [blockFetch]
             */
            blockOnFetch: suspend (Result<RequestType>, ResultType?) -> Unit = { _, _ -> },
        ) = ResourceNetworkBound(
            loadMode = loadMode,
            blockResult = blockResult,
            blockOnResult = blockOnResult,
            blockShouldFetch = blockShouldFetch,
            blockFetch = blockFetch,
            blockOnFetch = blockOnFetch
        ).asFlow()
    }

    enum class LoadMode {
        NETWORK_FIRST, LOCAL_FIRST
    }

    override suspend fun result() = blockResult()

    override suspend fun onResult(resultType: ResultType): Boolean {
        return blockOnResult(resultType)
    }

    override suspend fun shouldFetch(resultType: ResultType?): Boolean {
        return blockShouldFetch(resultType)
    }

    override suspend fun fetch() = blockFetch()

    override suspend fun onFetchSuccess(fetchType: RequestType) {
        blockOnFetch(Result.Success(fetchType), result()?.firstOrNull())
    }

    override suspend fun <T : Throwable> onFetchError(err: T, resultType: ResultType?) {
        blockOnFetch(Result.Error(error = err), resultType)
    }

    override suspend fun onFetchEmpty(resultType: ResultType?) {
        blockOnFetch(Result.Empty, resultType)
    }

    private suspend fun getResource(
        isLoading: Boolean = false,
        throwable: Throwable? = null,
        resultType: ResultType? = null
    ) = when {
        isLoading -> Resource.Loading
        throwable != null -> {
            when (throwable) {
                is ResourceNetworkBoundError -> throw throwable
                else -> Resource.Error(data = resultType, error = throwable)
            }
        }
        resultType != null && onResult(resultType) -> Resource.Success(resultType)
        else -> Resource.Empty
    }

    private suspend fun getResult(
        err: Throwable? = null, loading: Boolean = false
    ): Flow<Resource<ResultType>> = result()?.run {
        map { getResource(resultType = it, throwable = err, isLoading = loading) }
    } ?: flowOf(getResource(throwable = err))

    override fun asFlow() = flow {
        emit(getResource(isLoading = true))
        if (loadMode == LoadMode.LOCAL_FIRST) {
            val resultType = result()?.firstOrNull()
            if (shouldFetch(resultType)) {
                emit(getResource(resultType = resultType))
            } else {
                emitAll(getResult()); return@flow
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        val fetch = fetch()?.flatMapLatest { response ->
            when (response) {
                is Result.Success -> onFetchSuccess(response.data)
                    .run { getResult() }
                is Result.Error -> onFetchError(response.error, result()?.firstOrNull())
                    .run { getResult(err = response.error) }
                is Result.Empty -> onFetchEmpty(result()?.firstOrNull())
                    .run { getResult() }
            }
        } ?: throw ResourceNetworkBoundError(
            "(LoadMode == LoadMode.NETWORK_FIRST) || (shouldFetch(*) return true) fetch() can't return null"
        )
        emitAll(fetch)
    }.catch {
        emit(getResource(throwable = it))
    }.flowOn(Dispatchers.IO)
}