/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import androidx.viewpager2.widget.ViewPager2
import com.oldautumn.movie.data.api.model.ModelWithImage
import com.oldautumn.movie.data.api.model.ShowPlayedItem
import com.oldautumn.movie.data.api.model.ShowRecommendItem
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem
import com.oldautumn.movie.databinding.FragmentTvHomeBinding
import com.oldautumn.movie.ui.main.home.OffsetPageTransformer
import com.oldautumn.movie.ui.tv.TvDetailActivity
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTvHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val popularPager: ViewPager2 = binding.tvPopularPager
        val pagerIndicator: DotsIndicator = binding.tvDotsIndicator
        val tvTrendingAdapter =
            TvTrendingAdapter(
                mutableListOf(),
                object : (UnifyTvTrendingItem) -> Unit {
                    override fun invoke(movieWithImage: UnifyTvTrendingItem) {
                        val intent = Intent(requireContext(), TvDetailActivity::class.java)
                        intent.putExtra("tvId", movieWithImage.show.show.ids.tmdb)
                        startActivity(intent)
                    }
                },
            )

        val tvMostPlayedAdapter =
            TvMostPlayedAdapter(
                mutableListOf(),
                object : (ModelWithImage<ShowPlayedItem>) -> Unit {
                    override fun invoke(movieWithImage: ModelWithImage<ShowPlayedItem>) {
                        val intent = Intent(requireContext(), TvDetailActivity::class.java)
                        intent.putExtra("tvId", movieWithImage.show.show.ids.tmdb)
                        startActivity(intent)
                    }
                },
            )

        val tvMostRecommendAdapter =
            TvMostRecommendAdapter(
                mutableListOf(),
                object : (ModelWithImage<ShowRecommendItem>) -> Unit {
                    override fun invoke(movieWithImage: ModelWithImage<ShowRecommendItem>) {
                        val intent = Intent(requireContext(), TvDetailActivity::class.java)
                        intent.putExtra("tvId", movieWithImage.show.show.ids.tmdb)
                        startActivity(intent)
                    }
                },
            )

        val popularPagerAdapter =
            TvPopularPagerAdapter(
                object : (UnifyTvTrendingItem) -> Unit {
                    override fun invoke(trendingItem: UnifyTvTrendingItem) {
                        val intent = Intent(context, TvDetailActivity::class.java)
                        intent.putExtra("tvId", trendingItem.show.show.ids.tmdb)
                        startActivity(intent)
                    }
                },
            )
        binding.tvRecommendList.layoutManager =
            LinearLayoutManager(context, HORIZONTAL, false)
        binding.tvRecommendList.adapter = tvMostRecommendAdapter

        binding.tvMostPlayedList.layoutManager =
            LinearLayoutManager(context, HORIZONTAL, false)
        binding.tvMostPlayedList.adapter = tvMostPlayedAdapter

        popularPager.adapter = popularPagerAdapter

        popularPager.offscreenPageLimit = 1
        popularPager.setPageTransformer(OffsetPageTransformer(50, 15))
        pagerIndicator.attachTo(popularPager)

        viewModel.fetchPopularShow()
        viewModel.fetchTrendingShow()
        viewModel.fetchMostPlayedShowList("weekly")
        viewModel.fetchMostRecommendShowList("weekly")
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.errorMessage.isNotEmpty()) {
                        // 展示异常toast
                    } else {
                        if (uiState.trendingShowList.isNotEmpty()) {
                            tvTrendingAdapter.updateData(uiState.trendingShowList)
                        }
                        if (uiState.trendingShowList.isNotEmpty()) {
                            popularPagerAdapter.submitList(uiState.trendingShowList)
                        }
                        if (uiState.mostPlayedShowList.isNotEmpty()) {
                            tvMostPlayedAdapter.updateData(uiState.mostPlayedShowList)
                        }
                        if (uiState.mostRecommendShowList.isNotEmpty()) {
                            tvMostRecommendAdapter.updateData(uiState.mostRecommendShowList)
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
