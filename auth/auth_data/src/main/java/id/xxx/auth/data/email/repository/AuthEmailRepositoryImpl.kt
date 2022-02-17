package id.xxx.auth.data.email.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import id.xxx.auth.data.worker.Worker
import id.xxx.auth.data.email.source.local.LocalDataSource
import id.xxx.auth.data.email.source.mapper.toSignUpModel
import id.xxx.auth.data.email.source.mapper.toUserEntity
import id.xxx.auth.data.email.source.mapper.toUserModel
import id.xxx.auth.data.email.source.remote.RemoteDataSource
import id.xxx.auth.data.helper.Network
import id.xxx.auth.data.helper.get
import id.xxx.auth.domain.model.SignInModel
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.email.repository.AuthEmailRepository
import id.xxx.module.data.mediator.ResourceNetworkBound
import id.xxx.module.model.sealed.Resource
import id.xxx.module.model.sealed.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AuthEmailRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
    private val onConnected: Network
) : AuthEmailRepository {

    override fun signUp(email: String, password: String): Flow<Resource<SignUpModel>> {
        return ResourceNetworkBound.create(
            blockFetch = {
                remote.signUp(email, password)
            },
            blockOnFetch = { apiResponse, _ ->
                apiResponse.get(
                    blockSuccess = { local.signUp(it.toUserEntity(true)) }
                )
            },
            blockResult = {
                local.currentUser().map { it?.toSignUpModel() }
            }
        )
    }

    override fun isVerify() = ResourceNetworkBound.create(
        blockFetch = { remote.isVerify() },
        blockOnFetch = { apiResponse, _ ->
            apiResponse.get(
                blockSuccess = { local.setVerify(it) },
                blockEmpty = { local.setVerify(false) }
            )
        },
        blockResult = { local.currentUser().map { it?.isEmailVerify } }
    )

    override fun sendVerify(): Flow<Resource<String>> {
        val message = StringBuilder()
        val resource = ResourceNetworkBound.create(
            blockFetch = { remote.sendVerification() },
            blockOnFetch = { apiResponse, _ ->
                apiResponse.get(
                    blockSuccess = { message.replace(0, message.length, it) }
                )
            },
            blockResult = { flowOf(message.toString()) }
        )

        return resource
    }

    override fun signIn(email: String, password: String): Flow<Resource<SignInModel>> {
        return ResourceNetworkBound.create(
            loadMode = ResourceNetworkBound.LoadMode.NETWORK_FIRST,
            blockFetch = {
                remote.signIn(email, password)
            },
            blockOnFetch = { result, model ->
                when (result) {
                    is Result.Success -> {
                        local.signIn(result.data.toUserEntity(true))
                    }
                    is Result.Empty -> model?.apply { local.remove(email) }
                    is Result.Error -> {
                        if (result.error is FirebaseAuthInvalidUserException) local.remove(email)
                        if (result.error is FirebaseNetworkException) {
                            val userEntity = local.getUser(email).copy(isActive = true)
                            local.signIn(userEntity)
                            onConnected.onConnected(
                                Worker::class.java,
                                Worker.putData(email, password)
                            )
                        }
                    }
                }
            },
            blockResult = {
                local.signIn(email).map { it?.run { SignInModel(id, email, isEmailVerify) } }
            },
        )
    }

    override fun signOut() = ResourceNetworkBound.create(
        loadMode = ResourceNetworkBound.LoadMode.NETWORK_FIRST,
        blockFetch = { remote.signOut() },
        blockOnFetch = { apiResponse, _ ->
            apiResponse.get(
                blockSuccess = { local.signOut() }
            )
        },
        blockResult = { flow { emit(local.onSignOut()) } },
    )

    override fun currentUser() = ResourceNetworkBound.create(
        loadMode = ResourceNetworkBound.LoadMode.NETWORK_FIRST,
        blockFetch = { remote.currentUser() },
        blockResult = { local.currentUser().map { it?.run { it.toUserModel() } } }
    )
}