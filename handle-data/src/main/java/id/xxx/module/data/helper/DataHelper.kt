package id.xxx.module.data.helper

import id.xxx.module.domain.model.results.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataHelper<Type> private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: DataHelper<*>? = null

        @Suppress("UNCHECKED_CAST")
        @PublishedApi
        internal fun <Type> getInstance() =
            (INSTANCE ?: synchronized(DataHelper::class) {
                INSTANCE ?: DataHelper<Type>()
                    .also { INSTANCE = it }
            }) as DataHelper<Type>

        suspend inline fun <T> get(
            crossinline blockFetch: suspend () -> T?,
        ) = try {
            val data = blockFetch()
            if (data != null) {
                Results.Success(data)
            } else {
                Results.Error(NullPointerException())
            }
        } catch (e: Throwable) {
            Results.Error(e)
        }

        inline fun <ResultType> getAsFlow(
            crossinline blockFetch: suspend () -> ResultType?,
            crossinline blockOnFetch: (ResultType) -> Boolean = { true }
        ) = flow {
            val resultType = blockFetch()
            val result =
                if (resultType != null && blockOnFetch(resultType)) {
                    Results.Success(resultType)
                } else {
                    Results.Error(NullPointerException())
                }
            emit(result)
        }.catch { emit(Results.Error(it)) }

        inline fun <ResultType> getAsFlow(
            crossinline blockOnOpen: suspend (OnResult<ResultType>) -> Unit,
            crossinline blockOnClose: () -> Unit = {}
        ) = getInstance<ResultType>().stream(
            blockOnOpen = { blockOnOpen(it) },
            blockOnClose = { blockOnClose() }
        )
    }

    interface OnResult<T> {

        fun success(data: T)

        fun error(err: Throwable)
    }

    fun stream(
        blockOnOpen: suspend (OnResult<Type>) -> Unit,
        blockOnClose: () -> Unit = {}
    ) =
        callbackFlow {
            val onResult = object : OnResult<Type> {
                override fun success(data: Type) {
                    trySend(onResultSuccess(data))
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

    private fun onResultSuccess(data: Type): Results.Success<Type> {
        return Results.Success(data)
    }

    private fun onResultError(err: Throwable): Results.Error {
        return Results.Error(err)
    }
}