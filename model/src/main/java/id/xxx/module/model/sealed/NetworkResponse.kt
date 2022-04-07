package id.xxx.module.model.sealed

sealed class NetworkResponse<out R> {

    data class Success<out T>(val data: T) : NetworkResponse<T>()

    data class Error(val err: Throwable) : NetworkResponse<Nothing>()

    companion object {

        suspend fun <T, R : Throwable> NetworkResponse<T>.whenWithReturn(
            blockSuccess: suspend (T) -> R,
            blockError: suspend (Throwable) -> R,
        ) = when (this) {
            is Success -> blockSuccess(data)
            is Error -> blockError(err)
        }

        suspend fun <T> NetworkResponse<T>.whenNoReturn(
            blockSuccess: suspend (T) -> Unit = {},
            blockError: suspend (Throwable) -> Unit = {},
        ) = when (this) {
            is Success -> blockSuccess(data)
            is Error -> blockError(err)
        }
    }
}