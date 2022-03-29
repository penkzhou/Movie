package com.oldautumn.movie.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import coil.load
import com.oldautumn.movie.MovieUtils
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
import com.oldautumn.movie.databinding.ActivityMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", 0)
        if (movieId == 0) {
            finish()
        }
        viewModel.fetchMovieDetail(movieId)
        viewModel.fetchMovieCredit(movieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.movieDetail != null) {
                        binding.backdrop.load(
                            MovieUtils.getMovieBackdropUrl(
                                it.movieDetail.backdrop_path ?: ""
                            )
                        )
                        binding.title.text = it.movieDetail.title
                        binding.movieOverview.text = it.movieDetail.overview

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