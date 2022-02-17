package id.xxx.auth.data.email.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.xxx.module.model.IModel

@Entity(
    tableName = "user"
)
data class UserEntity(

    @PrimaryKey
    override val id: String,

    val isEmailVerify: Boolean,

    val email: String?,

    val isActive: Boolean = false,

    val photoStringUri: String?,

    val displayName: String?

) : IModel<String>