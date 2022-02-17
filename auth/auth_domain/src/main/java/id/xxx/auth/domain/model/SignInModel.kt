package id.xxx.auth.domain.model

import android.os.Parcelable
import id.xxx.module.model.IModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignInModel(

    override val id: String,

    val email: String,

    val isVerify: Boolean

) : IModel<String>, Parcelable