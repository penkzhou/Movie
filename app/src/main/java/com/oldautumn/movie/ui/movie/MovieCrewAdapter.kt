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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbCrew
import com.oldautumn.movie.databinding.ItemMovieCastBinding
import com.oldautumn.movie.utils.Utils

class MovieCrewAdapter(
    private val popularList: MutableList<TmdbCrew>,
    private val onItemClick: (TmdbCrew) -> Unit,
) :
    RecyclerView.Adapter<MovieCrewAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_cast, parent, false) as ViewGroup
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

    fun updateData(crewList: List<TmdbCrew>) {
        popularList.clear()
        popularList.addAll(crewList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class PopularViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieCastBinding.bind(itemView)
        private val moviePoster: ImageView = binding.movieCastPoster
        private val crewName: TextView = binding.movieCastName
        private val realName: TextView = binding.movieRealName
        private val profileDefaultName: TextView = binding.movieCastPosterName

        fun updateViewWithItem(crew: TmdbCrew) {
            if (crew.profile_path?.isNotEmpty() == true) {
                moviePoster.visibility = View.VISIBLE
                profileDefaultName.visibility = View.GONE
                moviePoster.load(Utils.getImageFullUrl(crew.profile_path)) {
                    transformations(
                        CircleCropTransformation(),
                    )
                }
            } else {
                moviePoster.visibility = View.GONE
                profileDefaultName.visibility = View.VISIBLE
                profileDefaultName.text = Utils.fetchFirstCharacter(crew.name)
            }
            moviePoster.contentDescription = "${crew.job}\n${crew.name}"
            crewName.text = crew.job
            realName.text = crew.name
        }
    }
}
