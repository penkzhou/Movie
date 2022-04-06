package com.oldautumn.movie.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Create the ViewGroup binding delegation
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(binding: (LayoutInflater, ViewGroup, Boolean) -> T): T {
    return binding(LayoutInflater.from(context), this, false)
}

fun <T, VIEW_BINDING : ViewBinding> RecyclerView.setup(
    items: MutableList<T>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> VIEW_BINDING,
    onItemClick: View.(T) -> Unit = {},
    bindHolder: View.(VIEW_BINDING?, T) -> Unit,
    manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
): ItemClickBaseAdapter<T, VIEW_BINDING> {
    val generalAdapter by lazy {
        ItemClickBaseAdapter<T, VIEW_BINDING>(
            items,
            bindingClass,
            { item: T ->
                onItemClick(item)
            },
            { binding: VIEW_BINDING?, item: T ->
                bindHolder(binding, item)
            },
        )
    }

    layoutManager = manager
    adapter = generalAdapter
    return generalAdapter
}