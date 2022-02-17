package id.xxx.module.domain.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class RecyclerViewViewHolder<T : ViewBinding>(
    open val binding: T
) : RecyclerView.ViewHolder(binding.root)