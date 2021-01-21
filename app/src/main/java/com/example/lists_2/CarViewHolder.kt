package com.example.lists_2

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class CarViewHolder(
    containerView: ViewBinding,
    onItemClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(containerView.root) {

    init {
        containerView.root.setOnClickListener {
            onItemClick(absoluteAdapterPosition)
        }
    }

    protected abstract fun bindMainInfo(automaker: String, model: String, carLink: String)
}