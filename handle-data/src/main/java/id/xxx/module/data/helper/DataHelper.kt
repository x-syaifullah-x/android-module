package id.xxx.module.data.helper

import id.xxx.module.model.sealed.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataHelper<Type> private constructor() {

    companion object {
        @Volatile
        private var instance: DataHelper<*>? = null

        @Suppress("UNCHECKED_CAST")
        @PublishedApi
        internal fun <Type> getInstance() =
            (instance ?: synchronized(DataHelper::class) {
                instance ?: DataHelper<Type>().also { instance = it }
            }) as DataHelper<Type>

        suspend inline fun <T> get(
            crossinline blockFetch: suspend () -> T?,
        ) = try {
            val data = blockFetch()
            if (data != null) Result.Success(data) else Result.Empty
        } catch (e: Throwable) {
            Result.Error(e)
        }

        inline fun <ResultType> getAsFlow(
            crossinline blockFetch: suspend () -> ResultType?,
            crossinline blockOnFetch: (ResultType) -> Boolean = { true }
        ) = flow {
            val resultType = blockFetch()
            val result =
                if (resultType != null && blockOnFetch(resultType)) Result.Success(resultType) else Result.Empty
            emit(result)
        }.catch { emit(Result.Error(it)) }

        inline fun <ResultType> getAsFlow(
            crossinline blockOnOpen: suspend (OnResult<ResultType>) -> Unit,
            crossinline blockOnClose: () -> Unit = {}
        ) = getInstance<ResultType>().stream(
            blockOnOpen = { blockOnOpen(it) },
            blockOnClose = { blockOnClose() }
        )
    }

    interface OnResult<T> {
        fun success(data: T?)
        fun error(err: Throwable)
    }

    fun stream(
        blockOnOpen: suspend (OnResult<Type>) -> Unit,
        blockOnClose: () -> Unit = {}
    ) =
        @Suppress("EXPERIMENTAL_API_USAGE")
        callbackFlow {
            val onResult = object : OnResult<Type> {
                override fun success(data: Type?) {
                    val result = data
                        ?.run { onResultSuccess(this) }
                        ?: onResultEmpty()
                    trySend(result)
                }

                override fun error(err: Throwable) {
                    trySend(onResultError(err))
                }
            }

            blockOnOpen(onResult)

            awaitClose { blockOnClose() }
        }.catch {
            emit(onResultError(it))
        }.flowOn(Dispatchers.IO)

    private fun onResultSuccess(data: Type): Result.Success<Type> {
        return Result.Success(data)
    }

    private fun onResultEmpty(): Result.Empty {
        return Result.Empty
    }

    private fun onResultError(err: Throwable): Result.Error {
        return Result.Error(err)
    }
}