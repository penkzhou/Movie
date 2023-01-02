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
import com.oldautumn.movie.data.api.model.TmdbCombinedCrew
import com.oldautumn.movie.databinding.ItemPeopleCreditBinding
import com.oldautumn.movie.utils.Utils

class PeopleCrewAdapter(
    private val list: MutableList<TmdbCombinedCrew>,
    private val onItemClick: (TmdbCombinedCrew) -> Unit
) :
    RecyclerView.Adapter<PeopleCrewAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_people_credit, parent, false)

        val holder = PopularViewHolder(rootView) {
            onItemClick(list[it])
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= list.size) {
            return
        }
        val movieTrendingItem = list[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(castList: List<TmdbCombinedCrew>) {
        list.clear()
        list.addAll(castList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PopularViewHolder(view: View, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(
        view
    ) {

        init {
            itemView.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        private val binding = ItemPeopleCreditBinding.bind(itemView)
        private val moviePoster: ImageView = binding.peopleCreditPoster
        private val castName: TextView = binding.peopleCreditName
        private val postName: TextView = binding.peopleCreditPosterName

        fun updateViewWithItem(cast: TmdbCombinedCrew) {
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
            moviePoster.contentDescription = "担任${cast.job}"
            castName.text = "担任${cast.job}"
        }
    }
}
