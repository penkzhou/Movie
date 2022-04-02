package com.oldautumn.movie.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
import com.oldautumn.movie.databinding.FragmentHomeBinding
import com.oldautumn.movie.ui.movie.MovieDetailActivity
import com.oldautumn.movie.ui.tv.TvDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val trendingListView: RecyclerView = binding.movieTrendingList
        val popularListView: RecyclerView = binding.moviePopularList
        val showTrendingListView: RecyclerView = binding.showTrendingList
        val showPopularListView: RecyclerView = binding.showPopularList
        val trendingAdapter = TrendingAdapter(mutableListOf())
        val showTrendingAdapter = ShowTrendingAdapter(mutableListOf())
        val popularAdapter =
            MoviePopularAdapter(mutableListOf(), object : MoviePopularAdapter.OnItemClickListener {
                override fun onItemClick(movie: MovieWithImage) {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.content.ids.tmdb)
                    intent.putExtra("movieSlug", movie.content.ids.slug)
                    startActivity(intent)
                }
            })
        val showPopularAdapter =
            MoviePopularAdapter(mutableListOf(), object : MoviePopularAdapter.OnItemClickListener {
                override fun onItemClick(movie: MovieWithImage) {
                    val intent = Intent(context, TvDetailActivity::class.java)
                    intent.putExtra("tvId", movie.content.ids.tmdb)
                    startActivity(intent)
                }
            })
        trendingListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        popularListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        showTrendingListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        showPopularListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        trendingListView.adapter = trendingAdapter
        popularListView.adapter = popularAdapter
        showTrendingListView.adapter = showTrendingAdapter
        showPopularListView.adapter = showPopularAdapter
        viewModel.fetchMovieData()
        viewModel.fetchPopularMovie()
        viewModel.fetchPopularShow()
        viewModel.fetchTrendingShow()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.errorMessage.isNotEmpty()) {
                        //展示异常toast

                    } else {
                        if (uiState.trendingMovieList.isNotEmpty()) {
                            trendingAdapter.updateData(uiState.trendingMovieList)
                        }
                        if (uiState.popularMovieList.isNotEmpty()) {
                            popularAdapter.updateData(uiState.popularMovieList)
                        }
                        if (uiState.trendingShowList.isNotEmpty()) {
                            showTrendingAdapter.updateData(uiState.trendingShowList)
                        }
                        if (uiState.popularShowList.isNotEmpty()) {
                            showPopularAdapter.updateData(uiState.popularShowList)
                        }
                    }

                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(
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