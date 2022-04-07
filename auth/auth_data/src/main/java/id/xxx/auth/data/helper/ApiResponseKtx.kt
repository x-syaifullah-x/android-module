package id.xxx.auth.data.helper

import id.xxx.module.model.sealed.NetworkResponse

suspend fun <T> NetworkResponse<T>.get(
    blockSuccess: suspend (T) -> Unit = {},
    blockError: suspend (Throwable) -> Unit = {},
) = when (this) {
    is NetworkResponse.Success -> blockSuccess(this.data)
    is NetworkResponse.Error -> blockError(this.err)
}