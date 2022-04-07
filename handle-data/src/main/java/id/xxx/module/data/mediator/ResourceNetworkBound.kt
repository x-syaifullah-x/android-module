package id.xxx.module.data.mediator

import id.xxx.module.data.mediator.base.AbstractResource
import id.xxx.module.data.mediator.exception.ResourceNetworkBoundError
import id.xxx.module.domain.model.results.Results
import id.xxx.module.domain.model.resources.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class ResourceNetworkBound<ResultType, RequestType> private constructor(
    private val loadMode: LoadMode,
    private val blockResult: suspend () -> Flow<ResultType?>?,
    private val blockOnResult: suspend (ResultType) -> Boolean,
    private val blockShouldFetch: (ResultType?) -> Boolean,
    private val blockFetch: suspend () -> Flow<Results<RequestType>>?,
    private val blockOnFetch: suspend (Results<RequestType>, ResultType?) -> Unit,
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
             * @if [blockOnResult] is false [blockResult] always [Resources.Empty]
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
            blockFetch: suspend () -> Flow<Results<RequestType>>? = { null },
            /**
             * called after [blockFetch]
             */
            blockOnFetch: suspend (Results<RequestType>, ResultType?) -> Unit = { _, _ -> },
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
        blockOnFetch(Results.Success(fetchType), result()?.firstOrNull())
    }

    override suspend fun <T : Throwable> onFetchError(err: T, resultType: ResultType?) {
        blockOnFetch(Results.Error(error = err), resultType)
    }

    private suspend fun getResource(
        isLoading: Boolean = false,
        throwable: Throwable? = null,
        resultType: ResultType? = null
    ) = when {
        isLoading -> Resources.Loading
        throwable != null -> {
            when (throwable) {
                is ResourceNetworkBoundError -> throw throwable
                else -> Resources.Error(data = resultType, error = throwable)
            }
        }
        resultType != null && onResult(resultType) -> Resources.Success(resultType)
        else -> Resources.Empty
    }

    private suspend fun getResult(
        err: Throwable? = null, loading: Boolean = false
    ): Flow<Resources<ResultType>> = result()?.run {
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
                is Results.Success -> onFetchSuccess(response.result)
                    .run { getResult() }
                is Results.Error -> onFetchError(response.error, result()?.firstOrNull())
                    .run { getResult(err = response.error) }
            }
        } ?: throw ResourceNetworkBoundError(
            "(LoadMode == LoadMode.NETWORK_FIRST) || (shouldFetch(*) return true) fetch() can't return null"
        )
        emitAll(fetch)
    }.catch {
        emit(getResource(throwable = it))
    }.flowOn(Dispatchers.IO)
}