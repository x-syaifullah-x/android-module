package id.xxx.module.domain.model.results

interface Results<out R> {

    data class Success<out T>(val result: T) : Results<T>

    data class Error(val error: Throwable) : Results<Nothing>
}