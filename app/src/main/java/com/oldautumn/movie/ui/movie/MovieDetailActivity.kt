package com.oldautumn.movie.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.oldautumn.movie.MovieUtils
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieItem
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
import com.oldautumn.movie.databinding.ActivityMovieDetailBinding
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", 0)
        if (movieId == 0) {
            finish()
        }

        val movieSlug = intent.getStringExtra("movieSlug") ?: ""

        val castAdapter = MovieCastAdapter(mutableListOf(), null)
        binding.movieCastList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieCastList.adapter = castAdapter


        val crewAdapter = MovieCrewAdapter(mutableListOf(), null)
        binding.movieCrewList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieCrewList.adapter = crewAdapter


        val recommendAdapter = MovieRecommendAdapter(mutableListOf(), object :
            MovieRecommendAdapter.OnItemClickListener {
            override fun onItemClick(movie: TmdbSimpleMovieItem) {
                val intent = Intent(this@MovieDetailActivity, MovieDetailActivity::class.java)
                intent.putExtra("movieId", movie.id)
                startActivity(intent)
            }
        })

        binding.movieRecommendList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieRecommendList.adapter = recommendAdapter


        val similarAdapter = MovieRecommendAdapter(mutableListOf(), object :
            MovieRecommendAdapter.OnItemClickListener {
            override fun onItemClick(movie: TmdbSimpleMovieItem) {
                val intent = Intent(this@MovieDetailActivity, MovieDetailActivity::class.java)
                intent.putExtra("movieId", movie.id)
                startActivity(intent)
            }
        })
        binding.movieSimilarList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.movieSimilarList.adapter = similarAdapter

        viewModel.fetchMovieDetail(movieId)
        viewModel.fetchMovieCredit(movieId)
        if (movieSlug.isBlank()) {
            viewModel.fetchTraktMovieDetail(movieSlug)
        }
        viewModel.fetchTraktMovieDetail(movieSlug)
        viewModel.fetchRecommendMovieList(movieId)
        viewModel.fetchSimilarMovieList(movieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.movieDetail != null) {
                        binding.backdrop.load(
                            MovieUtils.getMovieBackdropUrl(
                                it.movieDetail.backdrop_path ?: ""
                            )
                        )
                        binding.moviePoster.load(
                            MovieUtils.getMoviePosterUrl(
                                it.movieDetail.poster_path ?: ""
                            )
                        )
                        if (movieSlug.isBlank()) {
                            viewModel.fetchTraktMovieDetail(it.movieDetail.imdb_id ?: "")
                        }

                        binding.movieReleaseValue.text = it.movieDetail.release_date
                        binding.movieLengthValue.text = "${it.movieDetail.runtime}m"
                        binding.movieStatusValue.text = it.movieDetail.status
                        binding.title.text = it.movieDetail.title
                        binding.movieOverview.text = it.movieDetail.overview
                        binding.movieTmdbRatingValue.text =
                            "${it.movieDetail.vote_average}\n${it.movieDetail.vote_count}人评分"

                    }
                    if (it.movieCreditList != null) {
                        castAdapter.updateData(it.movieCreditList.cast ?: mutableListOf())
                        crewAdapter.updateData(it.movieCreditList.crew ?: mutableListOf())
                    }
                    if (it.traktMovieDetail != null) {
                        binding.movieCertificateValue.text = it.traktMovieDetail.certification
                        binding.movieTraktRatingValue.text =
                            "${DecimalFormat("##.##%").format(it.traktMovieDetail.rating)}\n${it.traktMovieDetail.votes}人评分"
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

    var factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(
                MovieRepository(
                    MovieRemoteDataSource(
                        ApiProvider.getAuthedApiService(),
                        ApiProvider.getTmdbApiService()
                    )
                )
            ) as T
        }
    }
}