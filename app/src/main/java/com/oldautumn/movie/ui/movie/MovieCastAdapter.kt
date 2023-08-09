package com.oldautumn.movie.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbCast
import com.oldautumn.movie.databinding.ItemMovieCastBinding
import com.oldautumn.movie.utils.Utils

class MovieCastAdapter(
    private val popularList: MutableList<TmdbCast>,
    private val onItemClick: (TmdbCast) -> Unit,
) :
    RecyclerView.Adapter<MovieCastAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_cast, parent, false) as ViewGroup
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

    class PopularViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(
        view,
    ) {

        private val binding = ItemMovieCastBinding.bind(itemView)
        private val moviePoster: ImageView = binding.movieCastPoster
        private val castName: TextView = binding.movieCastName
        private val realName: TextView = binding.movieRealName
        private val profileDefaultName: TextView = binding.movieCastPosterName

        fun updateViewWithItem(cast: TmdbCast) {
            if (cast.profile_path?.isNotEmpty() == true) {
                moviePoster.visibility = View.VISIBLE
                profileDefaultName.visibility = View.GONE
                moviePoster.load(Utils.getImageFullUrl(cast.profile_path)) {
                    transformations(
                        CircleCropTransformation(),
                    )
                }
            } else {
                moviePoster.visibility = View.GONE
                profileDefaultName.visibility = View.VISIBLE
                profileDefaultName.text = Utils.fetchFirstCharacter(cast.name)
            }
            moviePoster.contentDescription = "${cast.character}\n由${cast.name}扮演"
            castName.text = cast.character
            realName.text = cast.name
        }
    }
}
