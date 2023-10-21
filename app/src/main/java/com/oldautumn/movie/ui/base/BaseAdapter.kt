/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<T> {
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

    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    @LayoutRes
    abstract fun getItemLayoutId(): Int

    class BaseViewHolder<T>(view: View, onItemClick: (T) -> Unit) : RecyclerView.ViewHolder(
        view,
    ) {
        init {
            itemView.setOnClickListener {
                onItemClick
            }
        }
    }
}
