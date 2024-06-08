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
package com.oldautumn.movie.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbImageItem
import com.oldautumn.movie.databinding.ItemMoviePosterBinding
import com.oldautumn.movie.utils.Utils

class MovieAlbumAdapter(
    private val popularList: MutableList<TmdbImageItem>,
    private val onItemClick: (TmdbImageItem) -> Unit
) : RecyclerView.Adapter<MovieAlbumAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_poster, parent, false) as ViewGroup
        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = popularList[position]
                onItemClick(item)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= popularList.size) {
            return
        }
        val movieTrendingItem = popularList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(castList: List<TmdbImageItem>) {
        popularList.clear()
        popularList.addAll(castList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = popularList.size

    class PopularViewHolder(view: ViewGroup) :
        RecyclerView.ViewHolder(
            view
        ) {
        private val binding = ItemMoviePosterBinding.bind(itemView)
        private val moviePoster: ImageView = binding.moviePosterPic

        fun updateViewWithItem(cast: TmdbImageItem) {
            if (cast.file_path != null && cast.file_path.isNotEmpty()) {
                moviePoster.visibility = View.VISIBLE
                moviePoster.load(Utils.getImageFullUrl(cast.file_path)) {
                    transformations(
                        RoundedCornersTransformation(16f)
                    )
                }
            } else {
                moviePoster.visibility = View.GONE
            }
        }
    }
}
