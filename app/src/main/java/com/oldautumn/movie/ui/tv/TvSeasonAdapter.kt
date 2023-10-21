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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.Season
import com.oldautumn.movie.databinding.ItemTvSeasonBinding
import com.oldautumn.movie.utils.Utils

class TvSeasonAdapter(
    private val onDetailClick: (Season) -> Unit,
) :
    RecyclerView.Adapter<TvSeasonAdapter.SeasonViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SeasonViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tv_season,
                parent,
                false,
            )
        return SeasonViewHolder(root, onDetailClick)
    }

    override fun onBindViewHolder(
        holder: SeasonViewHolder,
        position: Int,
    ) {
        if (position < 0 || position >= differ.currentList.size) {
            return
        }
        val movieTrendingItem = differ.currentList[position]
        holder.bind(movieTrendingItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateData(seasonList: List<Season>) {
        this.differ.submitList(seasonList)
    }

    private val differCallback =
        object : DiffUtil.ItemCallback<Season>() {
            override fun areItemsTheSame(
                oldItem: Season,
                newItem: Season,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Season,
                newItem: Season,
            ): Boolean {
                return oldItem.equals(newItem)
            }
        }

    private val differ = AsyncListDiffer(this, differCallback)

    class SeasonViewHolder(itemView: View, private val onDetailClick: (Season) -> Unit) :
        RecyclerView.ViewHolder(
            itemView,
        ) {
        private val binding = ItemTvSeasonBinding.bind(itemView)

        fun bind(season: Season) {
            if (season.poster_path?.isNotEmpty() == true) {
                binding.tvSeasonPoster.load(Utils.getImageFullUrl(season.poster_path)) {
                    transformations(RoundedCornersTransformation(24f))
                }
            }
            binding.tvSeasonOverview.text = season.overview
            binding.tvSeasonEpisodeCount.text = "${season.episode_count}集"
            binding.tvSeasonReleaseDate.text = "${season.air_date}开播"
            binding.tvSeasonName.text = season.name
            binding.tvSeasonDetail.setOnClickListener {
                onDetailClick(season)
            }
        }
    }
}
