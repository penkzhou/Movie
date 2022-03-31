package com.oldautumn.movie.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.utils.MovieUtils
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem

class TrendingAdapter(private val popularList: MutableList<UnifyMovieTrendingItem>) :
    RecyclerView.Adapter<TrendingAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        return PopularViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= popularList.size) {
            return
        }
        val movieTrendingItem = popularList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(newPopularList: List<UnifyMovieTrendingItem>) {
        popularList.clear()
        popularList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)

        fun updateViewWithItem(movieWithImage: UnifyMovieTrendingItem) {
            moviePoster.contentDescription = movieWithImage.movie.movie.title
            moviePoster.load(MovieUtils.getMoviePosterUrl(movieWithImage.image.posters[0].file_path)) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }
}