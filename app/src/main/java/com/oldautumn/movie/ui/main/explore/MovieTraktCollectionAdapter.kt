package com.oldautumn.movie.ui.main.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TraktCollection
import com.oldautumn.movie.databinding.ItemMovieTraktCollectionBinding
import com.oldautumn.movie.utils.Utils

class MovieTraktCollectionAdapter(
    private val itemList: MutableList<TraktCollection>,
    private val onItemClick: (TraktCollection) -> Unit
) :
    RecyclerView.Adapter<MovieTraktCollectionAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_trakt_collection, parent, false)

        val holder = PopularViewHolder(rootView)
        rootView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = itemList[position]
                onItemClick(item)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        if (position < 0 || position >= itemList.size) {
            return
        }
        val movieTrendingItem = itemList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }

    fun updateData(collectionList: List<TraktCollection>) {
        itemList.clear()
        itemList.addAll(collectionList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieTraktCollectionBinding.bind(view)

        fun updateViewWithItem(collection: TraktCollection) {
            binding.collectionComment.text = collection.comment_count.toString()
            binding.collectionLike.text = "${collection.like_count}"
            binding.collectionCreatedAt.text =
                "${collection.list.user.name}" +
                "·${Utils.getFormatTimeDisplay(collection.list.created_at)}"
            binding.collectionDescription.text = collection.list.description
            binding.collectionTitle.text = collection.list.name
        }
    }
}
