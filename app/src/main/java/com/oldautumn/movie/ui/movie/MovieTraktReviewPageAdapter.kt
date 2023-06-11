package com.oldautumn.movie.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.databinding.ItemLoadStateBinding
import com.oldautumn.movie.utils.Utils

class MovieTraktReviewPageAdapter :
    PagingDataAdapter<TraktReview, MovieTraktReviewPageAdapter.MovieTraktReviewViewHolder>(
        TraktReviewComparator
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTraktReviewViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_trakt_review, parent, false)

        return MovieTraktReviewViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MovieTraktReviewViewHolder, position: Int) {
        val movieTrendingItem = getItem(position)
        holder.updateViewWithItem(movieTrendingItem)
    }

    interface OnItemClickListener {
        fun onItemClick(movieItem: TraktReview)
    }

    class MovieTraktReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reviewAuthor: TextView = view.findViewById(R.id.review_author)
        private val reviewReply: TextView = view.findViewById(R.id.review_reply_count)
        private val reviewContent: TextView = view.findViewById(R.id.review_content)
        private val reviewTime: TextView = view.findViewById(R.id.review_time)
        private val reviewLike: TextView = view.findViewById(R.id.review_like)

        fun updateViewWithItem(traktReview: TraktReview?) {
            if (traktReview == null) {
                return
            }
            reviewAuthor.text = traktReview.user.name
            reviewReply.text = traktReview.replies.toString()
            reviewContent.text = traktReview.comment
            reviewTime.text = Utils.getFormatTimeDisplay(traktReview.created_at)
            reviewLike.text = traktReview.likes.toString()
        }
    }

    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
    ) {
        private val binding = ItemLoadStateBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.loadingItem
        private val errorMsg: TextView = binding.loadErrorMsg
        private val retry: Button = binding.retryButton
            .also {
                it.setOnClickListener { retry() }
            }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    class ReviewLoadStateAdapter(
        private val retry: () -> Unit
    ) : LoadStateAdapter<LoadStateViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
        ) = LoadStateViewHolder(parent, retry)

        override fun onBindViewHolder(
            holder: LoadStateViewHolder,
            loadState: LoadState
        ) = holder.bind(loadState)
    }

    object TraktReviewComparator : DiffUtil.ItemCallback<TraktReview>() {
        override fun areItemsTheSame(oldItem: TraktReview, newItem: TraktReview): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TraktReview, newItem: TraktReview): Boolean {
            return oldItem == newItem
        }
    }
}
