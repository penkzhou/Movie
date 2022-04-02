package com.oldautumn.movie.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
import com.oldautumn.movie.databinding.ActivityTraktReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TraktReviewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTraktReviewBinding

    private val viewModel: MovieTraktReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraktReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val traktMovieId = intent.getStringExtra("traktMovieId") ?: ""
        val traktMovieTitle = intent.getStringExtra("traktMovieTitle") ?: ""
        supportActionBar?.title = traktMovieTitle
        val adapter = MovieTraktReviewAdapter(mutableListOf(), null)
        binding.reviewList.layoutManager =
            LinearLayoutManager(this)
        binding.reviewList.adapter = adapter

        binding.reviewSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort_type_array,
            android.R.layout.simple_spinner_item
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
                viewModel.fetchTraktReviewList(traktMovieId, sortType)

            }
        }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}