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
import com.oldautumn.movie.data.api.model.TmdbCrew

class MovieCrewAdapter(
    private val popularList: MutableList<TmdbCrew>,
    private val onItemClickListener: OnItemClickListener?
) :
    RecyclerView.Adapter<MovieCrewAdapter.PopularViewHolder>() {
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

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_cast_poster)
        private val crewName: TextView = view.findViewById(R.id.movie_cast_name)
        private val realName: TextView = view.findViewById(R.id.movie_real_name)

        fun updateViewWithItem(crew: TmdbCrew) {
            moviePoster.load(MovieUtils.getMoviePosterUrl(crew.profile_path ?: "")) {
                placeholder(R.mipmap.default_cast)
                transformations(RoundedCornersTransformation(16f))
            }
            crewName.text = crew.job
            realName.text = crew.name
        }
    }
}