package id.xxx.module.domain.adapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import id.xxx.module.domain.model.IModel

abstract class PagingAdapter<Model : IModel<*>, VH : RecyclerView.ViewHolder>(
    itemCallback: PagingAdapterItemCallback<Model> = PagingAdapterItemCallback()
) : PagingDataAdapter<Model, VH>(itemCallback)