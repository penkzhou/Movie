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
import coil.transform.CircleCropTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.Company
import com.oldautumn.movie.databinding.ItemSeasonCompanyBinding
import com.oldautumn.movie.utils.Utils

class ProductCompanyAdapter(
    private val popularList: MutableList<Company>,
    private val onItemClick: (Company) -> Unit
) : RecyclerView.Adapter<ProductCompanyAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_season_company, parent, false) as ViewGroup
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

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= popularList.size) {
            return
        }
        val movieTrendingItem = popularList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(castList: List<Company>) {
        popularList.clear()
        popularList.addAll(castList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = popularList.size

    interface OnItemClickListener {
        fun onItemClick(cast: Company)
    }

    class PopularViewHolder(view: ViewGroup) :
        RecyclerView.ViewHolder(
            view
        ) {
        private val binding = ItemSeasonCompanyBinding.bind(itemView)
        private val companyPoster: ImageView = binding.productCompanyPoster
        private val companyPosterName: TextView = binding.productCompanyPosterName
        private val companyName: TextView = binding.productCompanyName

        fun updateViewWithItem(cast: Company) {
            if (cast.logo_path?.isNotEmpty() == true) {
                companyPoster.visibility = View.VISIBLE
                companyPosterName.visibility = View.GONE
                companyPoster.load(Utils.getImageFullUrl(cast.logo_path)) {
                    transformations(
                        CircleCropTransformation()
                    )
                }
            } else {
                companyPoster.visibility = View.GONE
                companyPosterName.visibility = View.VISIBLE
                companyPosterName.text = Utils.fetchFirstCharacter(cast.name)
            }
            companyPoster.contentDescription = "${cast.name}"
            companyName.text = cast.name
        }
    }
}
