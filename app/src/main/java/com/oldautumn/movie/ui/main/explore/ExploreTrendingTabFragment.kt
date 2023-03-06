package com.oldautumn.movie.ui.main.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.oldautumn.movie.data.api.model.TraktCollection
import com.oldautumn.movie.databinding.FragmentExploreContentBinding
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreTrendingTabFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModels()
    private var _binding: FragmentExploreContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreContentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter =
            MovieTraktCollectionAdapter(
                mutableListOf(),
                object : (TraktCollection) -> Unit {
                    override fun invoke(p1: TraktCollection) {}
                }
            )
        binding.exploreList.adapter = adapter

        viewModel.fetchTrendingCollection()
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.errorMessage.isNotEmpty()) {
                        // 展示异常toast
                    } else {
                        if (uiState.trendingCollectionList.isNotEmpty()) {
                            adapter.updateData(uiState.trendingCollectionList)
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
