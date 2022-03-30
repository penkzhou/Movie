package com.oldautumn.movie.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
import com.oldautumn.movie.databinding.ActivityTraktReviewBinding
import kotlinx.coroutines.launch

class TraktReviewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTraktReviewBinding

    private val viewModel: MovieTraktReviewViewModel by lazy {
        ViewModelProvider(this, factory)[MovieTraktReviewViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraktReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val traktMovieId = intent.getStringExtra("traktMovieId") ?: ""
        val adapter = MovieTraktReviewAdapter(mutableListOf(), null)
        binding.reviewList.layoutManager =
            LinearLayoutManager(this)
        binding.reviewList.adapter = adapter
        viewModel.fetchTraktReviewList(traktMovieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.traktReviewList.isNotEmpty()) {
                        adapter.updateData(it.traktReviewList)
                    }
                }
            }
        }
    }


    var factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieTraktReviewViewModel(
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