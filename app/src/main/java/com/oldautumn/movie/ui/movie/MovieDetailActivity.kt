package com.oldautumn.movie.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieItem
import com.oldautumn.movie.databinding.ActivityMovieDetailBinding
import com.oldautumn.movie.ui.people.PeopleDetailActivity
import com.oldautumn.movie.utils.Utils
import com.oldautumn.movie.utils.Utils.loadWithPalette
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    private var chipColor: Int = R.color.purple_200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.home.setOnClickListener {
            finish()
        }

        val castAdapter =
            MovieCastAdapter(mutableListOf()) {
                val intent = Intent(this, PeopleDetailActivity::class.java)
                intent.putExtra("peopleId", it.id)
                startActivity(intent)
            }

        binding.movieCastList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieCastList.adapter = castAdapter

        val crewAdapter = MovieCrewAdapter(mutableListOf()) {
            val intent = Intent(this, PeopleDetailActivity::class.java)
            intent.putExtra("peopleId", it.id)
            startActivity(intent)
        }
        binding.movieCrewList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieCrewList.adapter = crewAdapter

        val movieAlbumAdapter = MovieAlbumAdapter(mutableListOf()) {
        }

        binding.movieAlbumList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.movieAlbumList.adapter = movieAlbumAdapter

        val movieBackdropAdapter = MovieAlbumAdapter(mutableListOf()) {
        }

        binding.movieBackdropList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieBackdropList.adapter = movieBackdropAdapter

        val recommendAdapter = MovieRecommendAdapter(
            mutableListOf(),
            object :
                MovieRecommendAdapter.OnItemClickListener {
                override fun onItemClick(movieItem: TmdbSimpleMovieItem) {
                    val intent =
                        Intent(
                            this@MovieDetailActivity,
                            MovieDetailActivity::class.java,
                        )
                    intent.putExtra("movieId", movieItem.id)
                    startActivity(intent)
                }
            },
        )

        binding.movieRecommendList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieRecommendList.adapter = recommendAdapter

        val similarAdapter = MovieRecommendAdapter(
            mutableListOf(),
            object :
                MovieRecommendAdapter.OnItemClickListener {
                override fun onItemClick(movie: TmdbSimpleMovieItem) {
                    val intent =
                        Intent(
                            this@MovieDetailActivity,
                            MovieDetailActivity::class.java,
                        )
                    intent.putExtra("movieId", movie.id)
                    startActivity(intent)
                }
            },
        )
        binding.movieSimilarList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieSimilarList.adapter = similarAdapter

        viewModel.fetchMovieDetailData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.movieDetail != null) {
                        binding.backdrop.loadWithPalette(
                            Utils.getImageFullUrl(
                                it.movieDetail.backdrop_path ?: "",
                            ),
                            paletteCallback = { palette ->
                                val swatch = palette.vibrantSwatch
                                if (swatch != null) {
                                    binding.backdrop.setBackgroundColor(swatch.rgb)
                                    binding.title.setTextColor(swatch.titleTextColor)
                                    binding.home.setColorFilter(
                                        swatch.titleTextColor,
                                        android.graphics.PorterDuff.Mode.MULTIPLY,
                                    )
                                    updateStatusBarBg(swatch.rgb)
                                    chipColor = swatch.rgb
                                    binding.movieGenre.children?.forEach { chip ->
                                        if (chip is Chip) {
                                            chip.setBackgroundColor(swatch.rgb)
                                        }
                                    }
                                }
                            },
                        )

                        binding.moviePoster.load(
                            Utils.getImageFullUrl(
                                it.movieDetail.poster_path ?: "",
                            ),
                        ) {
                            transformations(RoundedCornersTransformation(12f))
                        }
                        if (it.movieDetail.genres.isNotEmpty()) {
                            binding.movieGenre.removeAllViews()
                            it.movieDetail.genres.forEach {
                                binding.movieGenre.addView(
                                    Chip(this@MovieDetailActivity).apply {
                                        text = it.name
                                    },
                                )
                            }
                        }
                        binding.movieRevenueValue.text =
                            "$${NumberFormat.getIntegerInstance().format(it.movieDetail.revenue)}"
                        binding.movieReleaseValue.text = it.movieDetail.release_date
                        binding.movieLengthValue.text = "${it.movieDetail.runtime}m"
                        binding.movieStatusValue.text = it.movieDetail.status
                        binding.title.text = it.movieDetail.title
                        binding.movieOverview.text = it.movieDetail.overview
                        binding.movieReleaseCountry.text =
                            it.movieDetail.production_countries?.joinToString(",") {
                                    country ->
                                country.name
                            }
                        binding.movieTmdbRatingValue.text =
                            "${it.movieDetail.vote_average}\n${it.movieDetail.vote_count}人评分"
                    }
                    if (it.movieCreditList != null) {
                        if (it.movieCreditList.cast.isNotEmpty()) {
                            castAdapter.updateData(it.movieCreditList.cast)
                        }
                        if (it.movieCreditList.crew.isNotEmpty()) {
                            crewAdapter.updateData(it.movieCreditList.crew)
                        }
                    }

                    if (it.movieAlbum != null) {
                        if (it.movieAlbum.posters.isNotEmpty()) {
                            movieAlbumAdapter.updateData(it.movieAlbum.posters)
                        }

                        if (it.movieAlbum.backdrops.isNotEmpty()) {
                            movieBackdropAdapter.updateData(it.movieAlbum.backdrops)
                        }
                    }
                    var s = ""
                    s.length
                    if (it.traktMovieDetail != null) {
                        val traktMovieIds = it.traktMovieDetail.ids.trakt
                        val movieTitle = it.traktMovieDetail.title
                        binding.movieCertificateValue.text = it.traktMovieDetail.certification
                        binding.movieTraktRatingValue.text =
                            "${DecimalFormat("##.#").format(it.traktMovieDetail.rating)}" +
                            "\n" +
                            "${it.traktMovieDetail.votes}人评分"

                        binding.movieTraktRatingValue.setOnClickListener {
                            val intent =
                                Intent(
                                    this@MovieDetailActivity,
                                    MovieReviewActivity::class.java,
                                )
                            intent.putExtra("traktMovieId", traktMovieIds.toString())
                            intent.putExtra("traktMovieTitle", movieTitle)
                            startActivity(intent)
                        }
                    }

                    if (it.recommendMovieList != null) {
                        recommendAdapter.updateData(it.recommendMovieList.results)
                    }

                    if (it.similarMovieList != null) {
                        similarAdapter.updateData(it.similarMovieList.results)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initSystemBarsByAndroidX()
    }

    private fun initSystemBarsByAndroidX() {
        var controller = WindowCompat.getInsetsController(window, window.decorView)
        // 设置状态栏反色
//        controller?.isAppearanceLightStatusBars = true
//        controller?.hide(WindowInsetsCompat.Type.statusBars())
        // 隐藏状态栏
//        controller?.hide(WindowInsets.Type.statusBars())
//        // 显示状态栏
//        controller?.show(WindowInsets.Type.statusBars())
//        // 隐藏导航栏
//        controller?.hide(WindowInsets.Type.navigationBars())
//        // 显示导航栏
//        controller?.show(WindowInsets.Type.navigationBars())
        // 同时隐藏状态栏和导航栏
//        controller?.hide(WindowInsetsCompat.Type.statusBars())
        // 同时隐藏状态栏和导航栏
//        controller?.show(WindowInsets.Type.systemBars())
    }

    fun updateStatusBarBg(customStatusBarColor: Int) {
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        // finally change the color
//        window.setStatusBarColor(ContextCompat.getColor(this, customStatusBarColor))
    }
}
