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
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem

class TvTrendingAdapter(
    private val trendingList: MutableList<UnifyTvTrendingItem>,
    private val onItemClick: (item: UnifyTvTrendingItem) -> Unit
) :
    RecyclerView.Adapter<TvTrendingAdapter.TvTrendingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvTrendingViewHolder {

        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        val holder = TvTrendingViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(trendingList[position])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: TvTrendingViewHolder, position: Int) {
        if (position < 0 || position >= trendingList.size) {
            return
        }
        val movieTrendingItem = trendingList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(newPopularList: List<UnifyTvTrendingItem>) {
        trendingList.clear()
        trendingList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    class TvTrendingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.movie_poster)

        fun updateViewWithItem(movieWithImage: UnifyTvTrendingItem) {

            moviePoster.contentDescription = movieWithImage.show.show.title
            moviePoster.load(Utils.getImageFullUrl(movieWithImage.image.posters[0].file_path)) {
                placeholder(R.mipmap.default_poster)
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }
}