package com.oldautumn.movie.ui.main.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.utils.Utils
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.MovieWithImage

class TvPopularAdapter(
    private val popularList: MutableList<MovieWithImage>,
    private val onItemClick: (item: MovieWithImage) -> Unit
) :
    RecyclerView.Adapter<TvPopularAdapter.TvPopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvPopularViewHolder {

        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        val holder = TvPopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(popularList[position])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: TvPopularViewHolder, position: Int) {
        if (position < 0 || position >= popularList.size) {
            return
        }
        val movieTrendingItem = popularList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(newPopularList: List<MovieWithImage>) {
        popularList.clear()
        popularList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class TvPopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)


        fun updateViewWithItem(movieWithImage: MovieWithImage) {

            moviePoster.contentDescription = movieWithImage.content.title
            moviePoster.load(Utils.getImageFullUrl(movieWithImage.image.posters[0].file_path)) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }
}