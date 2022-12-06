package com.oldautumn.movie.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.utils.Utils

class MoviePopularPagerAdapter(
    private val onItemClick: (item: MovieWithImage) -> Unit
) :
    RecyclerView.Adapter<MoviePopularPagerAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_popular_pager_movie, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = differ.currentList[position]
                onItemClick(movie)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >=  differ.currentList.size) {
            return
        }
        val movieTrendingItem =  differ.currentList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieWithImage>(){
        override fun areItemsTheSame(oldItem: MovieWithImage, newItem: MovieWithImage): Boolean {
            return  oldItem.content.ids.trakt == newItem.content.ids.trakt
        }

        override fun areContentsTheSame(oldItem: MovieWithImage, newItem: MovieWithImage): Boolean {
            return oldItem.equals(newItem)
        }

    }

    val differ = AsyncListDiffer(this,differCallback)



    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(movie: MovieWithImage)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        private val movieTitle: TextView = view.findViewById(R.id.movie_title)

        fun updateViewWithItem(movieWithImage: MovieWithImage) {
            moviePoster.load(Utils.getImageFullUrl(movieWithImage.image.backdrops[0].file_path)) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
            movieTitle.text = movieWithImage.content.title
            moviePoster.contentDescription = movieWithImage.content.title
        }
    }
}