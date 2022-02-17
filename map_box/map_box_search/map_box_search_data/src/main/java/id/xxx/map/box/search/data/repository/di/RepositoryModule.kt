package id.xxx.map.box.search.data.repository.di

import id.xxx.map.box.search.data.helper.Address
import id.xxx.map.box.search.data.repository.RepositoryImpl
import id.xxx.map.box.search.domain.repository.IRepository
import org.koin.dsl.module

internal object RepositoryModule {
    private val repositoryImplModule = module {
        single { Address.getInstance(get()) }
        single<IRepository> { RepositoryImpl(get(), get(), get()) }
    }

    val modules = listOf(repositoryImplModule)
}