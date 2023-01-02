package com.oldautumn.movie.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    val list: List<T> = listOf(),
    val onItemClick: (T) -> Unit,
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val rootView = LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false)
        val viewHolder = BaseViewHolder(rootView, onItemClick)
        rootView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(list[position])
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    @LayoutRes
    abstract fun getItemLayoutId(): Int

    class BaseViewHolder<T>(view: View, onItemClick: (T) -> Unit) : RecyclerView.ViewHolder(
        view
    ) {

        init {
            itemView.setOnClickListener {
                onItemClick
            }
        }
    }
}
