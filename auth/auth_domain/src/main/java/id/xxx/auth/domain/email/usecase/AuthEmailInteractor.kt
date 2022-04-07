package id.xxx.auth.domain.email.usecase

import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel
import id.xxx.module.domain.model.resources.Resources
import kotlinx.coroutines.flow.Flow

interface AuthEmailInteractor {

    fun signUp(email: String, password: String): Flow<Resources<SignUpModel>>

    fun isVerify(): Flow<Resources<Boolean>>

    fun sendVerify(): Flow<Resources<String>>

    fun signIn(email: String, password: String): Flow<Resources<SignInModel>>

    fun signOut(): Flow<Resources<Boolean>>

    fun currentUser(): Flow<Resources<UserModel>>
}