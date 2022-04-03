package com.oldautumn.movie.ui.main.home

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
import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieRevenueItem
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem
import com.oldautumn.movie.databinding.FragmentHomeBinding
import com.oldautumn.movie.ui.movie.MovieDetailActivity
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val trendingListView: RecyclerView = binding.movieTrendingList
        val popularListView: RecyclerView = binding.moviePopularList
        val revenueListView = binding.movieBoxList
        val trendingAdapter =
            TrendingAdapter(mutableListOf(), object : (UnifyMovieTrendingItem) -> Unit {
                override fun invoke(movie: UnifyMovieTrendingItem) {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.movie.movie.ids.tmdb)
                    intent.putExtra("movieSlug", movie.movie.movie.ids.slug)
                    startActivity(intent)
                }

            })
        val popularAdapter =
            MoviePopularAdapter(mutableListOf(), object : (MovieWithImage) -> Unit {
                override fun invoke(movie: MovieWithImage) {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.content.ids.tmdb)
                    intent.putExtra("movieSlug", movie.content.ids.slug)
                    startActivity(intent)
                }

            })
        val revenueAdapter =
            MovieBoxofficeAdapter(mutableListOf(), object : (UnifyMovieRevenueItem) -> Unit {
                override fun invoke(movie: UnifyMovieRevenueItem) {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.movie.movie.ids.tmdb)
                    intent.putExtra("movieSlug", movie.movie.movie.ids.slug)
                    startActivity(intent)
                }

            })
        trendingListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        popularListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        revenueListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        trendingListView.adapter = trendingAdapter
        popularListView.adapter = popularAdapter
        revenueListView.adapter = revenueAdapter
        viewModel.fetchMovieData()
        viewModel.fetchPopularMovie()
        viewModel.fetchBoxOfficeMovieList()
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
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
                        if (uiState.revenueMovieList.isNotEmpty()) {
                            revenueAdapter.updateData(uiState.revenueMovieList)
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
}