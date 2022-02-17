package id.xxx.auth.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import id.xxx.auth.domain.email.usecase.AuthEmailInteractor

class AuthEmailViewModel(private val authEmail: AuthEmailInteractor) {

    companion object {
        const val KEY_EMAIL_SIGN_IN = "KEY_EMAIL_SIGN_IN"
        const val KEY_PASSWORD_SIGN_IN = "KEY_PASSWORD_SIGN_IN"

        const val KEY_NAME_SIGN_UP = "KEY_NAME_SIGN_UP"
        const val KEY_EMAIL_SIGN_UP = "KEY_EMAIL_SIGN_UP"
        const val KEY_PASSWORD_SIGN_UP = "KEY_PASSWORD_SIGN_UP"
    }

    private val statSignUp = mutableMapOf(
        KEY_NAME_SIGN_UP to false,
        KEY_EMAIL_SIGN_UP to false,
        KEY_PASSWORD_SIGN_UP to false
    )

    private val statSignUpLiveData = MutableLiveData(statSignUp)

    fun getStatSignUpLiveData() = statSignUpLiveData.map { !it.containsValue(false) }

    fun setStateSignUp(key: String, value: Boolean) {
        if (statSignUp.containsKey(key)) {
            statSignUp[key] = value
            statSignUpLiveData.postValue(statSignUp)
        } else {
            throw Error("Field In $key Not Found")
        }
    }

    private val statSignIn = mutableMapOf(
        KEY_EMAIL_SIGN_IN to false,
        KEY_PASSWORD_SIGN_IN to false,
    )

    private val statSignInLiveData = MutableLiveData(statSignIn)

    fun getStatSignInLiveData() = statSignInLiveData.map { !it.containsValue(false) }

    fun setStateSignIn(key: String, value: Boolean) {
        if (statSignIn.containsKey(key)) {
            statSignIn[key] = value
            statSignInLiveData.postValue(statSignIn)
        } else {
            throw Error("Field In $key Not Found")
        }
    }

    fun createUser(email: String, password: String) =
        authEmail.signUp(email, password)

    fun sendVerify() =
        authEmail.sendVerify().asLiveData()

    fun isVerify() =
        authEmail.isVerify().asLiveData()

    fun signIn(email: String, password: String) =
        authEmail.signIn(email, password).asLiveData()
}