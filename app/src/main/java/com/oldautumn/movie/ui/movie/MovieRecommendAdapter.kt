package com.oldautumn.movie.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.MovieUtils
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieItem
import java.text.DecimalFormat

class MovieRecommendAdapter(
    private val recommendList: MutableList<TmdbSimpleMovieItem>,
    private val onItemClickListener: OnItemClickListener?
) :
    RecyclerView.Adapter<MovieRecommendAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_recommend, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = recommendList[position]
                onItemClickListener?.onItemClick(movie)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= recommendList.size) {
            return
        }
        val movieTrendingItem = recommendList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(movieList: List<TmdbSimpleMovieItem>) {
        recommendList.clear()
        recommendList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }

    interface OnItemClickListener {
        fun onItemClick(movieItem: TmdbSimpleMovieItem)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        private val movieRate: TextView = view.findViewById(R.id.movie_vote_average)

        fun updateViewWithItem(movieItem: TmdbSimpleMovieItem) {
            moviePoster.load(MovieUtils.getMoviePosterUrl(movieItem.poster_path ?: "")) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
            movieRate.text = "${DecimalFormat("##.#").format(movieItem.vote_average)}"
        }
    }
}