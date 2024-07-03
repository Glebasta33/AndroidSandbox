package com.github.gltrusov.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.gltrusov.EntryPoint
import com.github.gltrusov.core_navigation.databinding.ListItemEntryPointDefaultBinding

class DefaultEntryPointsAdapter<T : EntryPoint>(
    private val onItemClick: (entryPoint: T) -> Unit,
    diffUtil: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            val castedOld = oldItem as EntryPoint
            val castedNew = newItem as EntryPoint
            return castedOld == castedNew
        }
    }
) : ListAdapter<T, DefaultEntryPointsViewHolder<T>>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultEntryPointsViewHolder<T> {
        return DefaultEntryPointsViewHolder(
            binding = ListItemEntryPointDefaultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: DefaultEntryPointsViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }
}