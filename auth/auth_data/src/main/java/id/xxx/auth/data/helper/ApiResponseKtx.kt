package id.xxx.auth.data.helper

import id.xxx.module.domain.model.results.Results

suspend fun <T> Results<T>.get(
    blockSuccess: suspend (T) -> Unit = {},
    blockError: suspend (Throwable) -> Unit = {},
) = when (this) {
    is Results.Success -> blockSuccess(this.result)
    is Results.Error -> blockError(this.error)
}