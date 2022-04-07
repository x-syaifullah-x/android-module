package id.xxx.auth.domain.email.usecase

import id.xxx.auth.domain.email.repository.AuthEmailRepository
import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel
import id.xxx.module.domain.model.resources.Resources
import kotlinx.coroutines.flow.Flow

class AuthEmailInteractorImpl(
    private val authEmailRepository: AuthEmailRepository
) : AuthEmailInteractor {

    override fun signUp(email: String, password: String): Flow<Resources<SignUpModel>> =
        authEmailRepository.signUp(email, password)

    override fun isVerify(): Flow<Resources<Boolean>> =
        authEmailRepository.isVerify()

    override fun sendVerify(): Flow<Resources<String>> =
        authEmailRepository.sendVerify()

    override fun signIn(email: String, password: String): Flow<Resources<SignInModel>> =
        authEmailRepository.signIn(email, password)

    override fun signOut(): Flow<Resources<Boolean>> =
        authEmailRepository.signOut()

    override fun currentUser(): Flow<Resources<UserModel>> =
        authEmailRepository.currentUser()
}