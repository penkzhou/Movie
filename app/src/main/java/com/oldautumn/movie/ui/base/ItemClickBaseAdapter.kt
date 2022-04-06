package com.oldautumn.movie.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ItemClickBaseAdapter<T, VIEW_BINDING : ViewBinding>(
    val list: MutableList<T>,
    private val bindingClass: (LayoutInflater, ViewGroup, Boolean) -> VIEW_BINDING,
    private val onItemClick: (T) -> Unit,
    private val bindHolder: View.(VIEW_BINDING?, T) -> Unit
) :
    RecyclerView.Adapter<ItemClickBaseAdapter.ItemViewHolder<T>>() {

    var binding: VIEW_BINDING? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        this.binding = parent.viewBinding(bindingClass)

        val holder = ItemViewHolder<T>(binding!!.root) {
            onItemClick(list[it])
        }
        return holder
    }


    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<T>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(DiffUtilCallback(list, newItems))
    }

    fun update(items: List<T>) {
//        updateAdapterWithDiffResult(calculateDiff(items))
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: T) {
        list.toMutableList().add(item)
        notifyItemInserted(list.size)
    }

    fun remove(position: Int) {
        list.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {

        val item = list[position]
        holder.itemView.bind(item)
        if (position == holder.bindingAdapterPosition) {
            holder.itemView.bindHolder(binding, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder<T>(view: View, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(
        view
    ) {

        init {
            itemView.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }
    }

    protected open fun View.bind(item: T) {}
    protected open fun onViewRecycled(itemView: View) {}
}

internal class DiffUtilCallback<ITEM>(
    private val oldItems: List<ITEM>,
    private val newItems: List<ITEM>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}