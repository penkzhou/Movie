package com.oldautumn.movie.ui.tv

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.databinding.ActivityTvSeasonDetailBinding
import com.oldautumn.movie.ui.movie.MovieCastAdapter
import com.oldautumn.movie.ui.movie.MovieCrewAdapter
import com.oldautumn.movie.ui.people.PeopleDetailActivity
import com.oldautumn.movie.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvSeasonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvSeasonDetailBinding

    private val viewModel: TvSeasonDetailViewModel by viewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeasonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Season Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val castAdapter = MovieCastAdapter(mutableListOf()) {
            val intent = Intent(this, PeopleDetailActivity::class.java)
            intent.putExtra("peopleId", it.id)
            startActivity(intent)
        }
        binding.tvCastList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvCastList.adapter = castAdapter

        val crewAdapter = MovieCrewAdapter(mutableListOf()) {
            val intent = Intent(this, PeopleDetailActivity::class.java)
            intent.putExtra("peopleId", it.id)
            startActivity(intent)
        }
        binding.tvCrewList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvCrewList.adapter = crewAdapter

        val episodeAdapter = TvEpisodeAdapter({
//            val intent = Intent(this, TvSeasonDetailActivity::class.java)
//            intent.putExtra("seasonId", it.id)
//            intent.putExtra("tvId", it.tvId)
//            startActivity(intent)
        })
        binding.tvEpisodeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvEpisodeList.adapter = episodeAdapter

        viewModel.fetchTvSeasonDetail()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.tvSeasonDetail != null) {
                        if (it.tvSeasonDetail.episodes.isNotEmpty()) {
                            episodeAdapter.updateData(it.tvSeasonDetail.episodes)
                            it.tvSeasonDetail.episodes.map { episode ->
                                episode.crew
                            }.flatten().toSet().toList().let { crew ->
                                crewAdapter.updateData(crew)
                            }
                            it.tvSeasonDetail.episodes.map { episode ->
                                episode.guest_stars
                            }.flatten().toSet().toList().let { cast ->
                                castAdapter.updateData(cast)
                            }
                        }
                        binding.tvPoster.load(
                            Utils.getImageFullUrl(
                                it.tvSeasonDetail.poster_path ?: ""
                            )
                        ) {
                            transformations(RoundedCornersTransformation(12f))
                        }
                        binding.tvOverview.text = it.tvSeasonDetail.overview
                        binding.toolbar.title = it.tvSeasonDetail.name
                        binding.tvReleaseValue.text = it.tvSeasonDetail.air_date
                    }
                }
            }
        }
    }
}
