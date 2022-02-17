package id.xxx.auth.data.email.source.remote.response

data class UserResponse(
    val uid: String,
    val email: String?,
    val isEmailVerify: Boolean,
    val photoStringUri: String?,
    val displayName: String?
)