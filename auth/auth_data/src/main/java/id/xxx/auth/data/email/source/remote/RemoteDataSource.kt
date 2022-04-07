package id.xxx.auth.data.email.source.remote

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.messaging.FirebaseMessaging
import id.xxx.auth.data.email.source.mapper.toUserResponse
import id.xxx.auth.data.email.source.remote.response.UserResponse
import id.xxx.module.data.helper.DataHelper
import id.xxx.module.data.test.espresso.EspressoIdlingResource
import id.xxx.module.domain.model.results.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class RemoteDataSource(
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging
) {

    fun update(displayName: String? = null, photoUri: Uri? = null) = DataHelper.getAsFlow(
        blockFetch = {
            val builder = UserProfileChangeRequest.Builder()
            displayName?.apply { builder.displayName = this }
            photoUri?.apply { builder.photoUri = this }
            auth.currentUser?.updateProfile(builder.build())?.await()
        }
    )

    fun signUp(email: String, password: String): Flow<Results<UserResponse>> {
        val flow = DataHelper.getAsFlow(
            blockFetch = {
                val result = auth.createUserWithEmailAndPassword(email, password)
                    .await()?.user?.toUserResponse()
                EspressoIdlingResource.decrement()
                result
            }
        )
        EspressoIdlingResource.increment()
        return flow
    }

    fun sendVerification(): Flow<Results<String>> {
        val flow = DataHelper.getAsFlow(
            blockFetch = {
                if (auth.currentUser?.isEmailVerified == true) {
                    return@getAsFlow "is email verified"
                }
                auth.currentUser?.sendEmailVerification()?.await()
                return@getAsFlow "Please Check Your Mail And Open Link To Verify"
            }
        )

        return flow
    }

    fun isVerify() = DataHelper.getAsFlow(
        blockFetch = {
            auth.currentUser?.reload()?.await()
            auth.currentUser?.isEmailVerified
        }
    )

    fun signIn(email: String, password: String): Flow<Results<UserResponse>> {
        val flow = DataHelper.getAsFlow(
            blockFetch = {
                withTimeout(10000) {
                    auth.signInWithEmailAndPassword(email, password).await().user?.run {
                        messaging.subscribeToTopic(uid).await()
                        EspressoIdlingResource.decrement()
                        return@run toUserResponse()
                    }
                }
            }
        )
        EspressoIdlingResource.increment()
        return flow
    }

    fun signOut() = DataHelper.getAsFlow(
        blockFetch = {
            withTimeout(3000) {
                auth.currentUser?.run {
                    auth.signOut()
                    messaging.unsubscribeFromTopic(uid).await()
                    auth.currentUser == null
                } ?: true
            }
        }
    )

    fun currentUser() = DataHelper.getAsFlow(
        blockFetch = {
            withTimeout(3000) {
                auth.currentUser?.run {
                    messaging.subscribeToTopic(uid).await()
                    return@run toUserResponse()
                }
            }
        }
    )
}