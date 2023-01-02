package com.oldautumn.movie.ui.main.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.oldautumn.movie.data.api.model.TraktCollection
import com.oldautumn.movie.databinding.FragmentExploreBinding
import com.oldautumn.movie.ui.movie.MovieTraktCollectionAdapter
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModels()
    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter =
            MovieTraktCollectionAdapter(
                mutableListOf(),
                object : (TraktCollection) -> Unit {
                    override fun invoke(p1: TraktCollection) {


                    }
                }
            )
        binding.collectionList.adapter = adapter
        binding.collectionList.layoutManager = GridLayoutManager(context, 2)

        viewModel.fetchPopularCollection()
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.errorMessage.isNotEmpty()) {
                        // 展示异常toast
                    } else {
                        if (uiState.collectionList.isNotEmpty()) {
                            adapter.updateData(uiState.collectionList)
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
