package com.oldautumn.movie.ui.main.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.oldautumn.movie.ui.base.viewBinding

class BaseAdapter<T, VIEW_BINDING : ViewBinding>(
    private val bindingClass: (LayoutInflater, ViewGroup, Boolean) -> VIEW_BINDING,
    private val dataList: MutableList<T>,
    private val onItemClick: (item: T) -> Unit
) :
    RecyclerView.Adapter<BaseAdapter.BaseItemViewHolder<T, VIEW_BINDING>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseItemViewHolder<T, VIEW_BINDING> {
        var bindView = parent.viewBinding(bindingClass)

        val holder = BaseItemViewHolder<T, VIEW_BINDING>(bindView)
        bindView.root.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(dataList[position])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseItemViewHolder<T, VIEW_BINDING>, position: Int) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        val movieTrendingItem = dataList[position]
        holder.bind(movieTrendingItem)
    }

    fun updateData(newPopularList: List<T>) {
        dataList.clear()
        dataList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class BaseItemViewHolder<T, VIEW_BINDING : ViewBinding>(private val viewBinding: VIEW_BINDING) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: T) {
            with(viewBinding) {
            }
        }
    }
}
