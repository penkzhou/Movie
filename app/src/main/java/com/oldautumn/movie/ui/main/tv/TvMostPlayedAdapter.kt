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
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.ModelWithImage
import com.oldautumn.movie.data.api.model.ShowPlayedItem
import com.oldautumn.movie.databinding.ItemShowWithInfoBinding
import com.oldautumn.movie.utils.Utils

class TvMostPlayedAdapter(
    private val dataList: MutableList<ModelWithImage<ShowPlayedItem>>,
    private val onItemClick: (item: ModelWithImage<ShowPlayedItem>) -> Unit,
) :
    RecyclerView.Adapter<TvMostPlayedAdapter.TvRecommendViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TvRecommendViewHolder {
        var bindView =
            ItemShowWithInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        val holder = TvRecommendViewHolder(bindView)
        bindView.root.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(dataList[position])
            }
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: TvRecommendViewHolder,
        position: Int,
    ) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        val movieTrendingItem = dataList[position]
        holder.bind(movieTrendingItem)
    }

    fun updateData(newPopularList: List<ModelWithImage<ShowPlayedItem>>) {
        dataList.clear()
        dataList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class TvRecommendViewHolder(private val viewBinding: ItemShowWithInfoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: ModelWithImage<ShowPlayedItem>) {
            with(viewBinding) {
                tvPoster.contentDescription = item.show.show.title
                tvPoster.load(Utils.getImageFullUrl(item.image.posters[0].file_path)) {
                    placeholder(R.mipmap.default_poster)
                    transformations(RoundedCornersTransformation(16f))
                }
                if (item.show.play_count > 0) {
                    tvInfo.text = item.show.play_count.toString()
                    tvInfo.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.play_count,
                        0,
                        0,
                        0,
                    )
                    tvInfo.visibility = View.VISIBLE
                } else {
                    tvInfo.visibility = View.GONE
                }
            }
        }
    }
}
