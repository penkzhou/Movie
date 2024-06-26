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
package com.oldautumn.movie.ui.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbSimpleTvItem
import com.oldautumn.movie.utils.Utils
import java.text.DecimalFormat

class TvRecommendAdapter(
    private val recommendList: MutableList<TmdbSimpleTvItem>,
    private val onItemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<TvRecommendAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_recommend, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val tv = recommendList[position]
                onItemClickListener?.onItemClick(tv)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= recommendList.size) {
            return
        }
        val tvTrendingItem = recommendList[position]
        holder.updateViewWithItem(tvTrendingItem)
    }

    fun updateData(tvList: List<TmdbSimpleTvItem>) {
        recommendList.clear()
        recommendList.addAll(tvList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = recommendList.size

    interface OnItemClickListener {
        fun onItemClick(tvItem: TmdbSimpleTvItem)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPoster: ImageView = view.findViewById(R.id.movie_poster)
        private val tvRate: TextView = view.findViewById(R.id.movie_vote_average)

        fun updateViewWithItem(movieItem: TmdbSimpleTvItem) {
            tvPoster.load(movieItem.poster_path?.let { Utils.getImageFullUrl(it) }) {
                transformations(RoundedCornersTransformation(16f))
            }
            tvRate.text = "${DecimalFormat("##.#").format(movieItem.vote_average)}"
        }
    }
}
