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
package com.oldautumn.movie.ui.main.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem
import com.oldautumn.movie.utils.Utils

class TvPopularPagerAdapter(
    private val onItemClick: (item: UnifyTvTrendingItem) -> Unit,
) :
    RecyclerView.Adapter<TvPopularPagerAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_popular_pager_tv, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = differ.currentList[position]
                onItemClick(movie)
            }
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: PopularViewHolder,
        position: Int,
    ) {
        if (position < 0 || position >= differ.currentList.size) {
            return
        }
        val movieTrendingItem = differ.currentList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    private val differCallback =
        object : DiffUtil.ItemCallback<UnifyTvTrendingItem>() {
            override fun areItemsTheSame(
                oldItem: UnifyTvTrendingItem,
                newItem: UnifyTvTrendingItem,
            ): Boolean {
                return oldItem.show.show.ids.trakt == newItem.show.show.ids.trakt
            }

            override fun areContentsTheSame(
                oldItem: UnifyTvTrendingItem,
                newItem: UnifyTvTrendingItem,
            ): Boolean {
                return oldItem.equals(newItem)
            }
        }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<UnifyTvTrendingItem>) {
        differ.submitList(list)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(movie: UnifyTvTrendingItem)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        private val movieTitle: TextView = view.findViewById(R.id.movie_title)

        fun updateViewWithItem(mediaWithImage: UnifyTvTrendingItem) {
            moviePoster.load(Utils.getImageFullUrl(mediaWithImage.image.backdrops[0].file_path)) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
            movieTitle.text = mediaWithImage.show.show.title
            moviePoster.contentDescription = mediaWithImage.show.show.title
        }
    }
}
