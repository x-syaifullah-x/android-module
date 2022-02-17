package id.xxx.map.box.search.data.di

import id.xxx.map.box.search.data.repository.di.RepositoryModule
import id.xxx.map.box.search.data.source.di.LocalModule
import id.xxx.map.box.search.data.source.di.RemoteModule
import org.koin.core.module.Module

object SearchDataModule {
    val modules = mutableListOf<Module>().apply {
        addAll(RemoteModule.modules)
        addAll(LocalModule.modules)
        addAll(RepositoryModule.modules)
    }.toList()
}