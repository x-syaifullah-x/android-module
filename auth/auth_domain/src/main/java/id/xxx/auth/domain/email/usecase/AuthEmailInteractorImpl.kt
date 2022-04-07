package id.xxx.auth.domain.email.usecase

import id.xxx.auth.domain.email.repository.AuthEmailRepository
import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel
import id.xxx.module.model.sealed.ResourceSealed
import kotlinx.coroutines.flow.Flow

class AuthEmailInteractorImpl(
    private val authEmailRepository: AuthEmailRepository
) : AuthEmailInteractor {

    override fun signUp(email: String, password: String): Flow<ResourceSealed<SignUpModel>> =
        authEmailRepository.signUp(email, password)

    override fun isVerify(): Flow<ResourceSealed<Boolean>> =
        authEmailRepository.isVerify()

    override fun sendVerify(): Flow<ResourceSealed<String>> =
        authEmailRepository.sendVerify()

    override fun signIn(email: String, password: String): Flow<ResourceSealed<SignInModel>> =
        authEmailRepository.signIn(email, password)

    override fun signOut(): Flow<ResourceSealed<Boolean>> =
        authEmailRepository.signOut()

    override fun currentUser(): Flow<ResourceSealed<UserModel>> =
        authEmailRepository.currentUser()
}