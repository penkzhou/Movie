package com.oldautumn.movie.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.oldautumn.movie.data.api.model.MediaWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieRevenueItem
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem
import com.oldautumn.movie.databinding.FragmentHomeBinding
import com.oldautumn.movie.ui.movie.MovieDetailActivity
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
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
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val trendingListView: RecyclerView = binding.movieTrendingList
        val popularPager: ViewPager2 = binding.moviePopularPager
        val pagerIndicator: DotsIndicator = binding.dotsIndicator
        val revenueListView = binding.movieBoxList
        val trendingAdapter =
            TrendingAdapter(
                mutableListOf(),
                object : (UnifyMovieTrendingItem) -> Unit {
                    override fun invoke(movie: UnifyMovieTrendingItem) {
                        val intent = Intent(context, MovieDetailActivity::class.java)
                        intent.putExtra("movieId", movie.movie.movie.ids.tmdb)
                        intent.putExtra("movieSlug", movie.movie.movie.ids.slug)
                        startActivity(intent)
                    }
                }
            )

        val popularPagerAdapter = MoviePopularPagerAdapter(object : (MediaWithImage) -> Unit {
            override fun invoke(movie: MediaWithImage) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movieId", movie.content.ids.tmdb)
                intent.putExtra("movieSlug", movie.content.ids.slug)
                startActivity(intent)
            }
        })
        val revenueAdapter =
            MovieBoxofficeAdapter(
                mutableListOf(),
                object : (UnifyMovieRevenueItem) -> Unit {
                    override fun invoke(movie: UnifyMovieRevenueItem) {
                        val intent = Intent(context, MovieDetailActivity::class.java)
                        intent.putExtra("movieId", movie.movie.movie.ids.tmdb)
                        intent.putExtra("movieSlug", movie.movie.movie.ids.slug)
                        startActivity(intent)
                    }
                }
            )
        trendingListView.layoutManager =
            LinearLayoutManager(context, HORIZONTAL, false)
        revenueListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        trendingListView.adapter = trendingAdapter
        revenueListView.adapter = revenueAdapter

        popularPager.adapter = popularPagerAdapter
        popularPager.offscreenPageLimit = 1
        popularPager.setPageTransformer(OffsetPageTransformer(50, 15))
        pagerIndicator.attachTo(popularPager)
        viewModel.fetchMovieData()
        viewModel.fetchPopularMovie()
        viewModel.fetchBoxOfficeMovieList()
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.errorMessage.isNotEmpty()) {
                        // 展示异常toast
                    } else {
                        if (uiState.trendingMovieList.isNotEmpty()) {
                            trendingAdapter.updateData(uiState.trendingMovieList)
                        }
                        if (uiState.popularMovieList.isNotEmpty()) {
                            popularPagerAdapter.differ.submitList(uiState.popularMovieList)
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

    class TimerHandler(private val weakReference: WeakReference<HomeFragment>) :
        Handler(Looper.getMainLooper())
}
