package com.oldautumn.movie.ui.main.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.ModelWithImage
import com.oldautumn.movie.data.api.model.ShowRecommendItem
import com.oldautumn.movie.databinding.ItemShowWithInfoBinding
import com.oldautumn.movie.utils.Utils

class TvMostRecommendAdapter(
    private val dataList: MutableList<ModelWithImage<ShowRecommendItem>>,
    private val onItemClick: (item: ModelWithImage<ShowRecommendItem>) -> Unit
) :
    RecyclerView.Adapter<TvMostRecommendAdapter.TvRecommendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvRecommendViewHolder {
        var bindView =
            ItemShowWithInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        val holder = TvRecommendViewHolder(bindView)
        bindView.root.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(dataList[position])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: TvRecommendViewHolder, position: Int) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        val movieTrendingItem = dataList[position]
        holder.bind(movieTrendingItem)
    }

    fun updateData(newPopularList: List<ModelWithImage<ShowRecommendItem>>) {
        dataList.clear()
        dataList.addAll(newPopularList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class TvRecommendViewHolder(private val viewBinding: ItemShowWithInfoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: ModelWithImage<ShowRecommendItem>) {
            with(viewBinding) {
                tvPoster.contentDescription = item.show.show.title
                tvPoster.load(Utils.getImageFullUrl(item.image.posters[0].file_path)) {
                    placeholder(R.mipmap.default_poster)
                    transformations(RoundedCornersTransformation(16f))
                }
                if (item.show.user_count > 0) {
                    tvInfo.text = item.show.user_count.toString()
                    tvInfo.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.recommend_tv,
                        0,
                        0,
                        0
                    )
                    tvInfo.visibility = View.VISIBLE
                } else {
                    tvInfo.visibility = View.GONE
                }
            }
        }
    }
}