package id.xxx.map.box.search.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.map.box.search.domain.model.PlacesModel
import id.xxx.map.box.search.presentation.R
import id.xxx.map.box.search.presentation.adapter.SearchAdapter
import id.xxx.map.box.search.presentation.databinding.FragmentSearchBinding
import id.xxx.map.box.search.presentation.ui.SearchActivity.Companion.DATA_EXTRA
import id.xxx.module.model.sealed.ResourceSealed
import id.xxx.module.model.sealed.ResourceSealed.Companion.whenNoReturn
import id.xxx.module.presentation.base.ktx.setResult
import id.xxx.module.view.binding.ktx.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding<FragmentSearchBinding>()

    private val viewModel by sharedViewModel<SearchViewModel>()

    private val adapter = SearchAdapter { _, model -> onItemClick(model) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = this@SearchFragment.adapter
        }

        viewModel.searchAutoComplete.observe(viewLifecycleOwner, this::statSearch)
    }

    private fun statSearch(resourceSealed: ResourceSealed<List<PlacesModel>>) {
        binding.pbLoading.isVisible = resourceSealed is ResourceSealed.Loading
        binding.groupEmpty.isVisible = resourceSealed is ResourceSealed.Empty
        resourceSealed.whenNoReturn(
            blockSuccess = { adapter.submitData(it) },
            blockEmpty = { adapter.submitData(listOf()) },
            blockError = { data, e ->
                if (!data.isNullOrEmpty()) {
                    adapter.submitData(data)
                } else {
                    Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            },
        )
    }

    private fun onItemClick(placesModel: PlacesModel) {
        requireActivity().setResult { putExtra(DATA_EXTRA, placesModel) }
    }
}