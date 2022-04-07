package id.xxx.module.domain.adapter

import androidx.recyclerview.widget.DiffUtil
import id.xxx.module.domain.model.IModel

class PagingAdapterItemCallback<T : IModel<*>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: T, newItem: T) = (oldItem == newItem)
}