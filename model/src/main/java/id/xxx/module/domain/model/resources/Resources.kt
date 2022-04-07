package id.xxx.module.domain.model.resources

interface Resources<out R> {

    object Loading : Resources<Nothing>

    data class Success<out T>(val result: T) : Resources<T>

    data class Error(val error: Throwable) : Resources<Nothing>
}