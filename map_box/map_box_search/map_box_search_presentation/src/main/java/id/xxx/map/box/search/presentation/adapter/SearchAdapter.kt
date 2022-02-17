package id.xxx.map.box.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.xxx.map.box.search.domain.model.PlacesModel
import id.xxx.map.box.search.presentation.databinding.ItemSearchBinding

class SearchAdapter(
    private val blockOnItemClick: (ItemSearchBinding, PlacesModel) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<BaseViewHolder<ItemSearchBinding>>() {

    init {
        setHasStableIds(true)
    }

    private val data = mutableListOf<PlacesModel>()

    fun submitData(data: List<PlacesModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BaseViewHolder<ItemSearchBinding>, position: Int) {
        val data = data[position]
        holder.binding.data = data
        holder.binding.apply {
            root.setOnClickListener { blockOnItemClick(this, data) }
        }
    }

    override fun getItemCount() = data.size

    override fun getItemId(position: Int) = data[position].hashCode().toLong()
}