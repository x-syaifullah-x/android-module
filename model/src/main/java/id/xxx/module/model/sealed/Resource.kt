package id.xxx.module.model.sealed

sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    data class Error<out T>(val data: T? = null, val error: Throwable) : Resource<T>()

    companion object {
        fun <T> Resource<T>?.whenNoReturn(
            blockLoading: () -> Unit = {},
            blockEmpty: () -> Unit = {},
            blockSuccess: (T) -> Unit = {},
            blockError: (T?, Throwable) -> Unit = { _, _ -> }
        ) = when (this) {
            is Loading -> blockLoading()
            is Success -> blockSuccess(data)
            is Empty -> blockEmpty()
            is Error -> blockError(data, error)
            else -> blockEmpty()
        }

        fun <T, R> Resource<T>?.whenWithReturn(
            blockLoading: () -> R,
            blockSuccess: (T?) -> R,
            blockError: (T?, Throwable) -> R,
            blockEmpty: () -> R,
        ) = when (this) {
            is Loading -> blockLoading()
            is Empty -> blockEmpty()
            is Success -> blockSuccess(data)
            is Error -> blockError(data, error)
            else -> blockEmpty()
        }
    }
}