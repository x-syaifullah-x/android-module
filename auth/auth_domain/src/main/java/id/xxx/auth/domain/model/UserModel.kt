package id.xxx.auth.domain.model

import android.os.Parcelable
import id.xxx.module.domain.model.IModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(

    override val id: String,

    val isEmailVerify: Boolean,

    val email: String?,

    val photoStringUri: String?,

    val displayName: String?

) : IModel<String>, Parcelable