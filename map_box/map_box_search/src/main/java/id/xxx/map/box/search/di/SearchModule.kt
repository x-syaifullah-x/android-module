package id.xxx.map.box.search.di

import id.xxx.map.box.search.data.di.SearchDataModule
import id.xxx.map.box.search.domain.di.SearchDomainModule
import id.xxx.map.box.search.presentation.di.SearchPresentationModule
import org.koin.core.module.Module

object SearchModule {
    val modules: List<Module> = mutableListOf<Module>().apply {
        addAll(SearchDataModule.modules)
        addAll(SearchDomainModule.modules)
        addAll(SearchPresentationModule.modules)
    }
}