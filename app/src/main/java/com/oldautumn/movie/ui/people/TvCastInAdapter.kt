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

class TvCastInAdapter(
    private val popularList: MutableList<TmdbCombinedCast>,
    private val isTv: Boolean,
    private val onItemClick: (TmdbCombinedCast) -> Unit,
) :
    RecyclerView.Adapter<TvCastInAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_people_credit, parent, false) as ViewGroup
        val holder = PopularViewHolder(rootView, isTv)
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

    fun updateData(castList: List<TmdbCombinedCast>) {
        popularList.clear()
        popularList.addAll(castList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    interface OnItemClickListener {
        fun onItemClick(cast: TmdbCombinedCast)
    }

    class PopularViewHolder(view: ViewGroup, isTv: Boolean) : RecyclerView.ViewHolder(
        view,
    ) {
        private val isTv = isTv

        private val binding = ItemPeopleCreditBinding.bind(itemView)
        private val moviePoster: ImageView = binding.peopleCreditPoster
        private val castName: TextView = binding.peopleCreditPosterName
        private val realName: TextView = binding.peopleCreditName

        fun updateViewWithItem(cast: TmdbCombinedCast) {
            val name = if (isTv) (cast.name) else cast.title
            if (cast.poster_path?.isNotEmpty() == true) {
                moviePoster.visibility = View.VISIBLE
                castName.visibility = View.GONE
                moviePoster.load(Utils.getImageFullUrl(cast.poster_path)) {
                    transformations(
                        RoundedCornersTransformation(16f),
                    )
                }
            } else {
                moviePoster.visibility = View.GONE
                castName.visibility = View.VISIBLE
                castName.text = Utils.fetchFirstCharacter(name)
            }
            moviePoster.contentDescription = "${cast.character}\n由${name}扮演"
            castName.text = cast.character
            realName.text = "在${name}中扮演\n${cast.character}"
        }
    }
}
