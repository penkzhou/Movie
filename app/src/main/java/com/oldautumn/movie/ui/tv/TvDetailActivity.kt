package com.oldautumn.movie.ui.tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbSimpleTvItem
import com.oldautumn.movie.databinding.ActivityTvDetailBinding
import com.oldautumn.movie.ui.movie.MovieCastAdapter
import com.oldautumn.movie.ui.movie.MovieCrewAdapter
import com.oldautumn.movie.ui.movie.MovieReviewActivity
import com.oldautumn.movie.ui.people.PeopleDetailActivity
import com.oldautumn.movie.utils.Utils
import com.oldautumn.movie.utils.Utils.loadWithPattle
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailBinding

    private val viewModel: TvDetailViewModel by viewModels()

    private var chipColor: Int = R.color.purple_200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTvDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.home.setOnClickListener {
            finish()
        }

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

        val seasonAdapter = TvSeasonAdapter {
            val intent = Intent(this, TvSeasonDetailActivity::class.java)
            intent.putExtra("seasonNumber", it.season_number)
            intent.putExtra("tvId", viewModel.currentTvId())
            startActivity(intent)
        }
        binding.tvSeasonList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvSeasonList.adapter = seasonAdapter

        val recommendAdapter = TvRecommendAdapter(
            mutableListOf(),
            object :
                TvRecommendAdapter.OnItemClickListener {
                override fun onItemClick(tvItem: TmdbSimpleTvItem) {
                    val intent =
                        Intent(
                            this@TvDetailActivity,
                            TvDetailActivity::class.java
                        )
                    intent.putExtra("tvId", tvItem.id)
                    startActivity(intent)
                }
            }
        )
        val companyAdapter = ProductCompanyAdapter(mutableListOf()) {}
        binding.tvProductCompanyList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvProductCompanyList.adapter = companyAdapter
        binding.tvRecommendList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvRecommendList.adapter = recommendAdapter

        val similarAdapter = TvRecommendAdapter(
            mutableListOf(),
            object :
                TvRecommendAdapter.OnItemClickListener {
                override fun onItemClick(tvItem: TmdbSimpleTvItem) {
                    val intent =
                        Intent(
                            this@TvDetailActivity,
                            TvDetailActivity::class.java
                        )
                    intent.putExtra("tvId", tvItem.id)
                    startActivity(intent)
                }
            }
        )
        binding.tvSimilarList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.tvSimilarList.adapter = similarAdapter

        viewModel.fetchTvDetailData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.tvDetail != null) {
                        if (it.tvDetail.backdrop_path != null) {
                            binding.backdrop.load(
                                Utils.getImageFullUrl(
                                    it.tvDetail.backdrop_path
                                )
                            )
                            binding.backdrop.loadWithPattle(
                                Utils.getImageFullUrl(
                                    it.tvDetail.backdrop_path
                                ),
                                paletteCallback = { palette ->
                                    val swatch = palette.vibrantSwatch
                                    if (swatch != null) {
                                        binding.backdrop.setBackgroundColor(swatch.rgb)
                                        binding.title.setTextColor(swatch.titleTextColor)
                                        binding.home.setColorFilter(
                                            swatch.titleTextColor,
                                            android.graphics.PorterDuff.Mode.MULTIPLY
                                        )
                                        chipColor = swatch.rgb
                                        binding.tvGenre.children?.forEach { chip ->
                                            if (chip is Chip) {
                                                chip.setBackgroundColor(swatch.rgb)
                                                chip.setTextColor(swatch.titleTextColor)
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        if (it.tvDetail.seasons.isNotEmpty()) {

                            seasonAdapter.updateData(it.tvDetail.seasons)
                        }
                        if (it.tvDetail.production_companies.isNotEmpty()) {
                            companyAdapter.updateData(it.tvDetail.production_companies)
                            binding.tvProductCompany.visibility = View.VISIBLE
                            binding.tvProductCompanyList.visibility = View.VISIBLE
                        } else {
                            binding.tvProductCompany.visibility = View.GONE
                            binding.tvProductCompanyList.visibility = View.GONE
                        }
                        binding.tvPoster.load(
                            Utils.getImageFullUrl(
                                it.tvDetail.poster_path ?: ""
                            )
                        ) {
                            transformations(RoundedCornersTransformation(12f))
                        }
                        if (it.tvDetail.genres.isNotEmpty()) {
                            binding.tvGenre.removeAllViews()
                            it.tvDetail.genres.forEach {
                                binding.tvGenre.addView(
                                    Chip(this@TvDetailActivity).apply {
                                        text = it.name
                                        setChipBackgroundColorResource(R.color.purple_200)
                                    }
                                )
                            }
                        }
                        if ((it.tvDetail.networks.firstOrNull()?.logo_path ?: "").isNotEmpty()) {
                            binding.tvNetworkIcon.load(
                                Utils.getImageFullUrl(
                                    it.tvDetail.networks.firstOrNull()?.logo_path
                                        ?: "",
                                    200
                                )
                            )
                            binding.tvNetworkIcon.visibility = View.VISIBLE
                            binding.tvNetworkValue.visibility = View.GONE
                        } else {
                            binding.tvNetworkIcon.visibility = View.GONE
                            binding.tvNetworkValue.visibility = View.VISIBLE
                        }

                        binding.tvNetworkValue.text =
                            it.tvDetail.networks.firstOrNull()?.name ?: ""
                        binding.tvReleaseValue.text = it.tvDetail.first_air_date
                        binding.tvLengthValue.text =
                            "${it.tvDetail.episode_run_time?.firstOrNull() ?: 0}m"
                        binding.tvStatusValue.text = it.tvDetail.status
                        binding.title.text = it.tvDetail.original_name
                        binding.tvOverview.text = it.tvDetail.overview
                        binding.tvTmdbRatingValue.text =
                            "${it.tvDetail.vote_average}\n${it.tvDetail.vote_count}人评分"
                    }
                    if (it.tvCreditList != null) {
                        if (it.tvCreditList.cast.isNotEmpty()) {
                            binding.tvCastList.visibility = View.VISIBLE
                            binding.tvCastTitle.visibility = View.VISIBLE
                            castAdapter.updateData(it.tvCreditList.cast)
                        } else {
                            binding.tvCastList.visibility = View.GONE
                            binding.tvCastTitle.visibility = View.GONE
                        }
                        if (it.tvCreditList.crew.isNotEmpty()) {
                            binding.tvCrewList.visibility = View.VISIBLE
                            binding.tvCrewTitle.visibility = View.VISIBLE
                            crewAdapter.updateData(it.tvCreditList.crew)
                        } else {
                            binding.tvCrewList.visibility = View.GONE
                            binding.tvCrewTitle.visibility = View.GONE
                        }
                    }
                    if (it.traktTvDetail != null) {
                        val traktTvIds = it.traktTvDetail.ids.trakt
                        val tvTitle = it.traktTvDetail.title
                        binding.tvCertificateValue.text = it.traktTvDetail.certification
                        binding.tvTraktRatingValue.text =
                            "${DecimalFormat("##.#").format(it.traktTvDetail.rating)}" +
                                    "\n" +
                                    "${it.traktTvDetail.votes}人评分"
                        binding.tvTraktRatingValue.setOnClickListener {
                            val intent =
                                Intent(
                                    this@TvDetailActivity,
                                    MovieReviewActivity::class.java
                                )
                            intent.putExtra("traktMovieId", traktTvIds.toString())
                            intent.putExtra("traktMovieTitle", tvTitle)
                            startActivity(intent)
                        }
                    }

                    if (it.recommendTvList != null) {
                        if (it.recommendTvList.results.isNotEmpty()) {
                            binding.tvRecommendTitle.visibility = View.VISIBLE
                            binding.tvRecommendList.visibility = View.VISIBLE
                            recommendAdapter.updateData(it.recommendTvList.results)
                        } else {
                            binding.tvRecommendTitle.visibility = View.GONE
                            binding.tvRecommendList.visibility = View.GONE
                        }
                    }

                    if (it.similarTvList != null) {
                        if (it.similarTvList.results.isNotEmpty()) {
                            binding.tvSimilarTitle.visibility = View.VISIBLE
                            binding.tvSimilarList.visibility = View.VISIBLE
                            similarAdapter.updateData(it.similarTvList.results)
                        } else {
                            binding.tvSimilarTitle.visibility = View.GONE
                            binding.tvSimilarList.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
