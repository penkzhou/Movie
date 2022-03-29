package com.oldautumn.movie.ui.movie

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import coil.load
import com.oldautumn.movie.MovieUtils
import com.oldautumn.movie.R
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

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
                        binding.toolbarLayout.title = it.movieDetail.title

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