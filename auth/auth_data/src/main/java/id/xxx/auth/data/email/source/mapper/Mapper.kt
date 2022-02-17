package id.xxx.auth.data.email.source.mapper

import com.google.firebase.auth.FirebaseUser
import id.xxx.auth.data.email.source.local.entity.UserEntity
import id.xxx.auth.data.email.source.remote.response.UserResponse
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.domain.model.UserModel

fun FirebaseUser.toUserResponse() = UserResponse(
    isEmailVerify = isEmailVerified,
    email = email,
    uid = uid,
    photoStringUri = photoUrl.toString(),
    displayName = displayName
)

fun UserResponse.toUserEntity(isActive: Boolean = false) = UserEntity(
    id = uid,
    isEmailVerify = isEmailVerify,
    email = email,
    isActive = isActive,
    photoStringUri = photoStringUri,
    displayName = displayName
)

fun UserEntity.toSignUpModel() = SignUpModel(
    id = id, email = email
)

fun UserEntity.toUserModel() = UserModel(
    id = id,
    isEmailVerify = isEmailVerify,
    email = email,
    photoStringUri = photoStringUri,
    displayName = displayName
)