package id.xxx.map.box.search.data.source.di

import id.xxx.map.box.search.data.BuildConfig
import id.xxx.map.box.search.data.source.remote.RemoteDataSource
import id.xxx.map.box.search.data.source.remote.service.ApiConfig
import id.xxx.map.box.search.data.source.remote.service.ApiService
import org.koin.dsl.module

internal object RemoteModule {

    private val apiServiceModule = module {
        single { ApiConfig.service(BuildConfig.BASE_URL).create(ApiService::class.java) }
    }

    private val remoteDataSourceModule = module {
        single { RemoteDataSource(get()) }
    }

    val modules = listOf(apiServiceModule, remoteDataSourceModule)
}