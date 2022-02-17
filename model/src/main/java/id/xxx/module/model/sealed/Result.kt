package id.xxx.module.model.sealed

sealed class Result<out R> {
    object Empty : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()

    companion object {

        suspend fun <T, R> Result<T>?.whenWithReturn(
            blockEmpty: suspend () -> R,
            blockSuccess: suspend (T) -> R,
            blockError: suspend (Throwable) -> R,
        ) = when (this) {
            is Empty -> blockEmpty()
            is Success -> blockSuccess(data)
            is Error -> blockError(error)
            else -> blockEmpty()
        }

        suspend fun <T> Result<T>?.whenNoReturn(
            blockEmpty: suspend () -> Unit = {},
            blockSuccess: suspend (T) -> Unit = {},
            blockError: suspend (Throwable) -> Unit = {},
        ) = when (this) {
            is Empty -> blockEmpty()
            is Success -> blockSuccess(data)
            is Error -> blockError(error)
            else -> blockEmpty()
        }
    }
}