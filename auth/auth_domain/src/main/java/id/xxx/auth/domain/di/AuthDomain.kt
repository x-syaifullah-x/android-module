package id.xxx.auth.domain.di

import id.xxx.auth.domain.email.usecase.AuthEmailInteractor
import id.xxx.auth.domain.email.usecase.AuthEmailInteractorImpl
import org.koin.dsl.module

object AuthDomain {

    val module = module {
        single<AuthEmailInteractor> { AuthEmailInteractorImpl(get()) }
    }
}