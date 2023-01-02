package com.oldautumn.movie.ui.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.Season
import com.oldautumn.movie.databinding.ItemTvSeasonBinding
import com.oldautumn.movie.utils.Utils

class TvSeasonAdapter(
    private val onDetailClick: () -> Unit,
    private val seasonList: MutableList<Season>
) :
    RecyclerView.Adapter<TvSeasonAdapter.SeasonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tv_season,
                parent, false
            )
        return SeasonViewHolder(
            root, onDetailClick
        )
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bind(seasonList[position])
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    fun updateData(seasonList: List<Season>) {
        this.seasonList.clear()
        this.seasonList.addAll(seasonList)
        notifyDataSetChanged()
    }

    class SeasonViewHolder(itemView: View, onDetailClick: () -> Unit) :
        RecyclerView.ViewHolder(
            itemView
        ) {

        private val binding = ItemTvSeasonBinding.bind(itemView)

        fun bind(season: Season) {
            if (season.poster_path?.isNotEmpty() == true) {
                binding.tvSeasonPoster.load(Utils.getImageFullUrl(season.poster_path)) {
                    transformations(RoundedCornersTransformation(24f))
                }
            }
            binding.tvSeasonOverview.text = season.overview
            binding.tvSeasonEpisodeCount.text = "${season.episode_count}集"
            binding.tvSeasonReleaseDate.text = "${season.air_date}开播"
            binding.tvSeasonName.text = season.name
            binding.tvSeasonDetail.setOnClickListener {
            }
        }
    }
}
