package id.xxx.map.box.search.presentation.di

import id.xxx.map.box.search.presentation.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SearchPresentationModule {
    private val viewModelModule = module {
        viewModel { SearchViewModel(get()) }
    }

    val modules = listOf(viewModelModule)
}