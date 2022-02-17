package id.xxx.auth.domain.email.usecase

import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel
import id.xxx.module.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface AuthEmailInteractor {

    fun signUp(email: String, password: String): Flow<Resource<SignUpModel>>

    fun isVerify(): Flow<Resource<Boolean>>

    fun sendVerify(): Flow<Resource<String>>

    fun signIn(email: String, password: String): Flow<Resource<SignInModel>>

    fun signOut(): Flow<Resource<Boolean>>

    fun currentUser(): Flow<Resource<UserModel>>
}