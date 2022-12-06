package com.oldautumn.movie.ui.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.MovieVideoItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MovieVideoAdapter(
) :
    RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_video, parent, false)
        return ViewHolder(rootView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < 0 || position >= differ.currentList.size) {
            return
        }
        val movieTrendingItem = differ.currentList[position]
        holder.updateViewWithItem(movieTrendingItem)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallback = object : DiffUtil.ItemCallback<MovieVideoItem>() {
        override fun areItemsTheSame(oldItem: MovieVideoItem, newItem: MovieVideoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieVideoItem, newItem: MovieVideoItem): Boolean {
            return oldItem.equals(newItem)
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onItemClick(movieItem: MovieVideoItem)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val videoPlayerView: YouTubePlayerView = view.findViewById(R.id.youtube_player_view)




        fun updateViewWithItem(movieItem: MovieVideoItem) {
            videoPlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
                override fun onApiChange(youTubePlayer: YouTubePlayer) {

                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

                }

                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError
                ) {
                    Log.i("YouTubePlayer", "onError:${error.name},error code is ${error.ordinal} ")
                }

                override fun onPlaybackQualityChange(
                    youTubePlayer: YouTubePlayer,
                    playbackQuality: PlayerConstants.PlaybackQuality
                ) {

                }

                override fun onPlaybackRateChange(
                    youTubePlayer: YouTubePlayer,
                    playbackRate: PlayerConstants.PlaybackRate
                ) {

                }

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(movieItem.id
                    ,0f)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    Log.i("YouTubePlayer", "onStateChange:${state.name} ")
                }

                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                    Log.i("YouTubePlayer", "onVideoDuration: ")
                }

                override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                    Log.i("YouTubePlayer", "onVideoId:$videoId ")
                }

                override fun onVideoLoadedFraction(
                    youTubePlayer: YouTubePlayer,
                    loadedFraction: Float
                ) {
                    Log.i("YouTubePlayer", "onVideoLoadedFraction:$loadedFraction ")
                }

            })


        }
    }
}