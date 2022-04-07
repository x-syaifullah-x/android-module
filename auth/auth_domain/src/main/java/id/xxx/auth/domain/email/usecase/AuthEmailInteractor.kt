package id.xxx.auth.domain.email.usecase

import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel
import id.xxx.module.model.sealed.ResourceSealed
import kotlinx.coroutines.flow.Flow

interface AuthEmailInteractor {

    fun signUp(email: String, password: String): Flow<ResourceSealed<SignUpModel>>

    fun isVerify(): Flow<ResourceSealed<Boolean>>

    fun sendVerify(): Flow<ResourceSealed<String>>

    fun signIn(email: String, password: String): Flow<ResourceSealed<SignInModel>>

    fun signOut(): Flow<ResourceSealed<Boolean>>

    fun currentUser(): Flow<ResourceSealed<UserModel>>
}