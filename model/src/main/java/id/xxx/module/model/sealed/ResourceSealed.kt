package id.xxx.module.model.sealed

sealed class ResourceSealed<out R> {

    object Loading : ResourceSealed<Nothing>()

    data class Success<out T>(val data: T) : ResourceSealed<T>()

    data class Error<out T>(val data: T? = null, val error: Throwable) : ResourceSealed<T>()

    object Empty : ResourceSealed<Nothing>()

    companion object {
        fun <T> ResourceSealed<T>?.whenNoReturn(
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

        fun <T, R> ResourceSealed<T>?.whenWithReturn(
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