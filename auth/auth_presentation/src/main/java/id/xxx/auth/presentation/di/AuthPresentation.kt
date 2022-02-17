package id.xxx.auth.presentation.di

import id.xxx.auth.presentation.ui.AuthEmailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
object AuthPresentation {

    val module = module {
        single { AuthEmailViewModel(get()) }
    }
}