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
import com.oldautumn.movie.data.api.model.Episode
import com.oldautumn.movie.databinding.ItemTvEpisodeBinding
import com.oldautumn.movie.utils.Utils

class TvEpisodeAdapter(
    private val onDetailClick: () -> Unit,
) : RecyclerView.Adapter<TvEpisodeAdapter.EpisodeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EpisodeViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tv_episode,
                parent,
                false,
            )
        return EpisodeViewHolder(
            root,
            onDetailClick,
        )
    }

    override fun onBindViewHolder(
        holder: EpisodeViewHolder,
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

    fun updateData(seasonList: List<Episode>) {
        this.differ.submitList(seasonList)
    }

    private val differCallback =
        object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(
                oldItem: Episode,
                newItem: Episode,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Episode,
                newItem: Episode,
            ): Boolean {
                return oldItem.equals(newItem)
            }
        }

    private val differ = AsyncListDiffer(this, differCallback)

    class EpisodeViewHolder(itemView: View, private val onDetailClick: () -> Unit) :
        RecyclerView.ViewHolder(
            itemView,
        ) {
        private val binding = ItemTvEpisodeBinding.bind(itemView)

        fun bind(season: Episode) {
            if (season.still_path.isNotEmpty() == true) {
                binding.tvEpisodePoster.load(Utils.getImageFullUrl(season.still_path)) {
                    transformations(RoundedCornersTransformation(24f))
                }
            }
            binding.tvEpisodeOverview.text = season.overview
            binding.tvEpisodeNumber.text = "第${season.episode_number}集(${season.runtime}m)"
            binding.tvEpisodeReleaseDate.text = "${season.air_date}开播"
            binding.tvEpisodeName.text = season.name
            binding.tvEpisodeDetail.setOnClickListener {
                onDetailClick()
            }
        }
    }
}
