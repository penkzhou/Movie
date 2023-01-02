package com.oldautumn.movie.ui.main.tv

import android.content.Intent
import android.os.Bundle
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
import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem
import com.oldautumn.movie.databinding.FragmentTvHomeBinding
import com.oldautumn.movie.ui.tv.TvDetailActivity
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvMainFragment : Fragment() {

    private val viewModel: TvMainViewModel by viewModels()
    private var _binding: FragmentTvHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTvHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val trendingListView: RecyclerView = binding.tvTrendingList
        val popularListView: RecyclerView = binding.tvPopularList
        val tvTrendingAdapter =
            TvTrendingAdapter(
                mutableListOf(),
                object : (UnifyTvTrendingItem) -> Unit {
                    override fun invoke(movieWithImage: UnifyTvTrendingItem) {
                        val intent = Intent(requireContext(), TvDetailActivity::class.java)
                        intent.putExtra("tvId", movieWithImage.show.show.ids.tmdb)
                        startActivity(intent)
                    }
                }
            )
        val popularAdapter =
            TvPopularAdapter(
                mutableListOf(),
                object : (MovieWithImage) -> Unit {
                    override fun invoke(movie: MovieWithImage) {
                        val intent = Intent(context, TvDetailActivity::class.java)
                        intent.putExtra("tvId", movie.content.ids.tmdb)
                        startActivity(intent)
                    }
                }
            )
        trendingListView.layoutManager =
            LinearLayoutManager(context, HORIZONTAL, false)
        popularListView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        trendingListView.adapter = tvTrendingAdapter
        popularListView.adapter = popularAdapter

        viewModel.fetchPopularShow()
        viewModel.fetchTrendingShow()
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.errorMessage.isNotEmpty()) {
                        // 展示异常toast
                    } else {
                        if (uiState.trendingShowList.isNotEmpty()) {
                            tvTrendingAdapter.updateData(uiState.trendingShowList)
                        }
                        if (uiState.popularShowList.isNotEmpty()) {
                            popularAdapter.updateData(uiState.popularShowList)
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
