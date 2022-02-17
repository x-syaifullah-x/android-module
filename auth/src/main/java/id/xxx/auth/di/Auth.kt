package id.xxx.auth.di

import id.xxx.auth.data.di.AutData
import id.xxx.auth.domain.di.AuthDomain
import id.xxx.auth.presentation.di.AuthPresentation

object Auth {

    val modules = listOf(
        AutData.module,
        AuthDomain.module,
        AuthPresentation.module
    )
}