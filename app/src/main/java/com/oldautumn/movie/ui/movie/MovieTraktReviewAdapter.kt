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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.utils.Utils

class MovieTraktReviewAdapter(
    private val reviewList: MutableList<TraktReview>,
    private val onItemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<MovieTraktReviewAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_trakt_review, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = reviewList[position]
                onItemClickListener?.onItemClick(movie)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= reviewList.size) {
            return
        }
        val movieTrendingItem = reviewList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(movieList: List<TraktReview>) {
        reviewList.clear()
        reviewList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = reviewList.size

    interface OnItemClickListener {
        fun onItemClick(movieItem: TraktReview)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reviewAuthor: TextView = view.findViewById(R.id.review_author)
        private val reviewReply: TextView = view.findViewById(R.id.review_reply_count)
        private val reviewContent: TextView = view.findViewById(R.id.review_content)
        private val reviewTime: TextView = view.findViewById(R.id.review_time)
        private val reviewLike: TextView = view.findViewById(R.id.review_like)

        fun updateViewWithItem(traktReview: TraktReview) {
            reviewAuthor.text = traktReview.user.name
            reviewReply.text = traktReview.replies.toString()
            reviewContent.text = traktReview.comment
            reviewTime.text = Utils.getFormatTimeDisplay(traktReview.created_at)
            reviewLike.text = traktReview.likes.toString()
        }
    }
}
