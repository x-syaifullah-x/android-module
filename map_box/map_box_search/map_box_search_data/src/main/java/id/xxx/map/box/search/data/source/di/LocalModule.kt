package id.xxx.map.box.search.data.source.di

import id.xxx.map.box.search.data.source.local.LocalDataSource
import org.koin.dsl.module

internal object LocalModule {

    private val localDataSourceModule = module {
        single { LocalDataSource(get()) }
    }

    val modules = listOf(localDataSourceModule)
}