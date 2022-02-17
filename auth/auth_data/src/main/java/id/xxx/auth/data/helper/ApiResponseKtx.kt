package id.xxx.auth.data.helper

import id.xxx.module.model.sealed.Result

suspend fun <T> Result<T>.get(
    blockSuccess: suspend (T) -> Unit = {},
    blockError: suspend (Throwable) -> Unit = {},
    blockEmpty: suspend () -> Unit = {},
) = when (this) {
    is Result.Success -> blockSuccess(this.data)
    is Result.Error -> blockError(this.error)
    is Result.Empty -> blockEmpty()
}