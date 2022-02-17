package id.xxx.map.box.search.domain.di

import id.xxx.map.box.search.domain.usecase.IInteractor
import id.xxx.map.box.search.domain.usecase.InteractorImpl
import org.koin.dsl.module

object SearchDomainModule {
    private val useCaseModule = module {
        single<IInteractor> { InteractorImpl(get()) }
    }

    val modules = listOf(useCaseModule)
}