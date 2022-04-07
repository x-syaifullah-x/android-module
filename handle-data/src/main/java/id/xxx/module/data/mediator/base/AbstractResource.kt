package id.xxx.module.data.mediator.base

import id.xxx.module.model.sealed.NetworkResponse
import id.xxx.module.model.sealed.ResourceSealed
import kotlinx.coroutines.flow.Flow

abstract class AbstractResource<ResultType, FetchType> {

    protected abstract suspend fun result(): Flow<ResultType?>?

    protected abstract suspend fun onResult(resultType: ResultType): Boolean

    protected abstract suspend fun shouldFetch(resultType: ResultType?): Boolean

    protected abstract suspend fun fetch(): Flow<NetworkResponse<FetchType?>>?

    protected abstract suspend fun onFetchSuccess(fetchType: FetchType)

    protected abstract suspend fun <T : Throwable> onFetchError(err: T, resultType: ResultType?)

    abstract fun asFlow(): Flow<ResourceSealed<ResultType>>
}