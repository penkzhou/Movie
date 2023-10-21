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
package com.oldautumn.movie.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem
import com.oldautumn.movie.utils.Utils

class TrendingAdapter(
    private val popularList: MutableList<UnifyMovieTrendingItem>,
    private val onItemClick: (item: UnifyMovieTrendingItem) -> Unit,
) :
    RecyclerView.Adapter<TrendingAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = popularList[position]
                onItemClick(movie)
            }
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: PopularViewHolder,
        position: Int,
    ) {
        if (position < 0 || position >= popularList.size) {
            return
        }
        val movieTrendingItem = popularList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(newPopularList: List<UnifyMovieTrendingItem>) {
        popularList.clear()
        popularList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)

        fun updateViewWithItem(movieWithImage: UnifyMovieTrendingItem) {
            moviePoster.contentDescription = movieWithImage.movie.movie.title
            moviePoster.load(Utils.getImageFullUrl(movieWithImage.image.posters[0].file_path)) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }
}
