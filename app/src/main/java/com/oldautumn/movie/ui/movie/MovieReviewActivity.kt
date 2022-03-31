package com.oldautumn.movie.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.databinding.ActivityTraktReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieReviewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTraktReviewBinding

    private val viewModel: MovieReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraktReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.reviewList.layoutManager =
            LinearLayoutManager(this)

        binding.reviewSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort_type_array,
            android.R.layout.simple_spinner_item
        )
        binding.bindState(
            uiState = viewModel.uiState,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (!it.title.isNullOrEmpty()) {
                        supportActionBar?.title = it.title
                    }
                }
            }
        }
    }

    private fun ActivityTraktReviewBinding.bindState(
        uiState: StateFlow<MovieReviewUiState>,
        pagingData: Flow<PagingData<TraktReview>>,
        uiActions: (MovieReviewViewModel.UiAction) -> Unit
    ) {
        val reviewAdapter = MovieTraktReviewPageAdapter()
        binding.reviewList.adapter = reviewAdapter.withLoadStateFooter(
            MovieTraktReviewPageAdapter.ReviewLoadStateAdapter { reviewAdapter.retry() }
        )

        binding.reviewSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val lines = resources.getStringArray(R.array.sort_type_array).toList()
                val sortType = lines[position]
                uiActions(MovieReviewViewModel.UiAction.ChangeType(sortType))

            }
        }

        lifecycleScope.launch {
            pagingData.collectLatest(reviewAdapter::submitData)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}