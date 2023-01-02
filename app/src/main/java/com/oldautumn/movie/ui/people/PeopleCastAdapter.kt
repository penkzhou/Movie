package com.oldautumn.movie.ui.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbCombinedCast
import com.oldautumn.movie.databinding.ItemPeopleCreditBinding
import com.oldautumn.movie.utils.Utils

class PeopleCastAdapter(
    private val popularList: MutableList<TmdbCombinedCast>,
    private val onItemClick: (TmdbCombinedCast) -> Unit
) :
    RecyclerView.Adapter<PeopleCastAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_people_credit, parent, false) as ViewGroup
        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = popularList[position]
                onItemClick(item)
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

    fun updateData(castList: List<TmdbCombinedCast>) {
        popularList.clear()
        popularList.addAll(castList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class PopularViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(
        view
    ) {

        private val binding = ItemPeopleCreditBinding.bind(itemView)
        private val moviePoster: ImageView = binding.peopleCreditPoster
        private val castName: TextView = binding.peopleCreditName
        private val postName: TextView = binding.peopleCreditPosterName

        fun updateViewWithItem(cast: TmdbCombinedCast) {
            if (cast.poster_path != null && cast.poster_path.isNotEmpty()) {
                moviePoster.visibility = View.VISIBLE
                postName.visibility = View.GONE
                moviePoster.load(Utils.getImageFullUrl(cast.poster_path)) {
                    transformations(
                        RoundedCornersTransformation(16f)
                    )
                }
            } else {
                moviePoster.visibility = View.GONE
                postName.visibility = View.VISIBLE
                postName.text = cast.title
            }
            moviePoster.contentDescription = cast.title
            castName.text = "扮演${cast.character}"
        }
    }
}
