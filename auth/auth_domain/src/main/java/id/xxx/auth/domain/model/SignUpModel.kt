package id.xxx.auth.domain.model

import id.xxx.module.model.IModel

data class SignUpModel(

    override val id: String,

    val email: String?

) : IModel<String>