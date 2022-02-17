package id.xxx.auth.presentation.helper

sealed class InputValidation<out R> {
    object Valid : InputValidation<Nothing>()
    data class NotValid<out T>(val message: String) : InputValidation<T>()
}