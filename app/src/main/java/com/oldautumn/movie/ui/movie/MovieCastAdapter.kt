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
import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.TmdbCast
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem

class MovieCastAdapter(
    private val popularList: MutableList<TmdbCast>,
    private val onItemClickListener: OnItemClickListener?
) :
    RecyclerView.Adapter<MovieCastAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_cast, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie = popularList[position]
                onItemClickListener?.onItemClick(movie)
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

    fun updateData(castList: List<TmdbCast>) {
        popularList.clear()
        popularList.addAll(castList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    interface OnItemClickListener {
        fun onItemClick(cast: TmdbCast)
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_cast_poster)
        private val castName: TextView = view.findViewById(R.id.movie_cast_name)
        private val realName: TextView = view.findViewById(R.id.movie_real_name)

        fun updateViewWithItem(cast: TmdbCast) {
            moviePoster.load(MovieUtils.getMoviePosterUrl(cast.profile_path ?: "")) {
                placeholder(R.mipmap.default_cast)
                transformations(RoundedCornersTransformation(16f))
            }
            moviePoster.contentDescription = "${cast.character}\n由${cast.name}扮演"
            castName.text = cast.character
            realName.text = "由${cast.name}扮演"
        }
    }
}