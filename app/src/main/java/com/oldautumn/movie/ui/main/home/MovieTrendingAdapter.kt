package com.oldautumn.movie.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.oldautumn.movie.utils.Utils
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem

class MovieTrendingAdapter(private val trendingList: MutableList<UnifyMovieTrendingItem>) :
    RecyclerView.Adapter<MovieTrendingAdapter.TrendingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {

        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trending_movie, parent, false)
        return TrendingViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        if (position < 0 || position >= trendingList.size) {
            return
        }
        val movieTrendingItem = trendingList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(newTrendingList: List<UnifyMovieTrendingItem>) {
        trendingList.clear()
        trendingList.addAll(newTrendingList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    class TrendingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieName: TextView = view.findViewById(R.id.movie_name)
        private val movieYear: TextView = view.findViewById(R.id.movie_year)
        private val movieWatcher: TextView = view.findViewById(R.id.movie_watchers)
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)

        fun updateViewWithItem(movieTrendingItem: UnifyMovieTrendingItem) {
            movieName.setText(movieTrendingItem.movie.movie.title)
            movieYear.setText(movieTrendingItem.movie.movie.year.toString())
            movieWatcher.setText("${movieTrendingItem.movie.watchers}人在看")
            moviePoster.load(Utils.getMoviePosterUrl(movieTrendingItem.image.backdrops[0].file_path))
        }
    }
}