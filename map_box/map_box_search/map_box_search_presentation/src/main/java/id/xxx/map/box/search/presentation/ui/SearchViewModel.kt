package id.xxx.map.box.search.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.xxx.map.box.search.domain.usecase.IInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(
    private val iInteractor: IInteractor
) : ViewModel() {

    private val stat = MutableStateFlow<String?>(null)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchAutoComplete = stat
        .debounce { it?.run { if (length <= 1) 0 else 500 } ?: 0 }
        .flatMapLatest { query -> iInteractor.getPlaces(query) }
        .asLiveData()

    val query = { value: String? -> stat.value = value }
}