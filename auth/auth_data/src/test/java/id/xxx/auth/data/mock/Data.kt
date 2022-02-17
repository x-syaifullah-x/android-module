package id.xxx.auth.data.mock

import id.xxx.auth.data.email.source.local.entity.UserEntity
import id.xxx.auth.data.email.source.remote.response.UserResponse

object Data {

    const val EMAIL = "x.syaifullah.x@gmail.com"
    const val PASS = "19021992"
    const val UID = "TEST_12345"
    const val PHOTO_URL = "photo_url"
    const val DISPLAY_NAME = "display_name"

    fun mockUserResponse(isEmailVerify: Boolean = false) =
        UserResponse(
            uid = UID,
            email = EMAIL,
            isEmailVerify = isEmailVerify,
            photoStringUri = PHOTO_URL,
            displayName = DISPLAY_NAME
        )

    fun mockUserEntity(isEmailVerify: Boolean = false) =
        UserEntity(id = UID, isEmailVerify = isEmailVerify, email = EMAIL, false, null, null)
}