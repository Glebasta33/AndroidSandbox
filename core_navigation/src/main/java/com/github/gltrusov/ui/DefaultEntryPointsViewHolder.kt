package com.github.gltrusov.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.gltrusov.EntryPoint
import com.github.gltrusov.core_navigation.databinding.ListItemEntryPointDefaultBinding

class DefaultEntryPointsViewHolder<T : EntryPoint>(
    private val binding: ListItemEntryPointDefaultBinding,
    private val onItemClick: (entryPoint: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var _entryPoint: T? = null

    init {
        binding.root.setOnClickListener { _entryPoint?.let(onItemClick) }
    }

    fun bind(entryPoint: T): Unit = with(binding) {
        _entryPoint = entryPoint
        entryPoint.iconResId?.let { entryPointIconImageView.setImageResource(it) }
        entryPointIconImageView.isVisible = entryPoint.iconResId != null
        entryPointTitleTextView.text = entryPoint.name
        entryPointDescriptionTextView.text = entryPoint.description
    }
}