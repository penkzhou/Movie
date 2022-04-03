package com.oldautumn.movie.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.oldautumn.movie.utils.Utils
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbCrew
import com.oldautumn.movie.databinding.ItemMovieCastBinding

class MovieCrewAdapter(
    private val popularList: MutableList<TmdbCrew>,
    private val onItemClickListener: OnItemClickListener?
) :
    RecyclerView.Adapter<MovieCrewAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val holder = PopularViewHolder(parent)
        parent.setOnClickListener {
            val position = holder.bindingAdapterPosition
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

    fun updateData(crewList: List<TmdbCrew>) {
        popularList.clear()
        popularList.addAll(crewList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    interface OnItemClickListener {
        fun onItemClick(crew: TmdbCrew)
    }

    class PopularViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(view.context)
            .inflate(R.layout.item_movie_cast, view, false)
    ) {

        private val binding = ItemMovieCastBinding.bind(itemView)
        private val moviePoster: ImageView = binding.movieCastPoster
        private val crewName: TextView = binding.movieCastName
        private val realName: TextView = binding.movieRealName
        private val profileDefaultName: TextView = binding.movieCastPosterName

        fun updateViewWithItem(crew: TmdbCrew) {
            if (crew.profile_path?.isNotEmpty() == true) {
                moviePoster.visibility = View.VISIBLE
                profileDefaultName.visibility = View.GONE
                moviePoster.load(Utils.getMoviePosterUrl(crew.profile_path)) {
                    transformations(
                        CircleCropTransformation(),
                    )
                }
            } else {
                moviePoster.visibility = View.GONE
                profileDefaultName.visibility = View.VISIBLE
                profileDefaultName.text = Utils.fetchFirstCharacter(crew.name)
            }
            moviePoster.contentDescription = "${crew.job}\n${crew.name}"
            crewName.text = crew.job
            realName.text = crew.name
        }
    }
}