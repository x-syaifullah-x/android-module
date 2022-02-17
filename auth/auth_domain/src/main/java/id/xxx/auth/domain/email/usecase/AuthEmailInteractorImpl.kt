package id.xxx.auth.domain.email.usecase

import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel
import id.xxx.auth.domain.email.repository.AuthEmailRepository
import id.xxx.module.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

class AuthEmailInteractorImpl(
    private val authEmailRepository: AuthEmailRepository
) : AuthEmailInteractor {

    override fun signUp(email: String, password: String): Flow<Resource<SignUpModel>> =
        authEmailRepository.signUp(email, password)

    override fun isVerify(): Flow<Resource<Boolean>> =
        authEmailRepository.isVerify()

    override fun sendVerify(): Flow<Resource<String>> =
        authEmailRepository.sendVerify()

    override fun signIn(email: String, password: String): Flow<Resource<SignInModel>> =
        authEmailRepository.signIn(email, password)

    override fun signOut(): Flow<Resource<Boolean>> =
        authEmailRepository.signOut()

    override fun currentUser(): Flow<Resource<UserModel>> =
        authEmailRepository.currentUser()
}