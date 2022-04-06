package com.oldautumn.movie.ui.people

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import com.oldautumn.movie.utils.Utils
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.TmdbCombinedCast
import com.oldautumn.movie.data.api.model.TmdbCombinedCrew
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieItem
import com.oldautumn.movie.databinding.ActivityMovieDetailBinding
import com.oldautumn.movie.databinding.ActivityPeopleDetailBinding
import com.oldautumn.movie.ui.movie.*
import com.oldautumn.movie.ui.tv.TvDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat

@AndroidEntryPoint
class PeopleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeopleDetailBinding

    private val viewModel: PeopleDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPeopleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val movieCastAdapter =
            PeopleCastAdapter(mutableListOf(), object : (TmdbCombinedCast) -> Unit {
                override fun invoke(p1: TmdbCombinedCast) {
                    val intent = Intent(this@PeopleDetailActivity, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", p1.id)
                    startActivity(intent)
                }
            })
        binding.peopleCastInMovieList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.peopleCastInMovieList.adapter = movieCastAdapter


        val movieCrewAdapter =
            PeopleCrewAdapter(mutableListOf(), object : (TmdbCombinedCrew) -> Unit {
                override fun invoke(p1: TmdbCombinedCrew) {
                    val intent = Intent(this@PeopleDetailActivity, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", p1.id)
                    startActivity(intent)
                }
            })
        binding.peopleCrewInMovieList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.peopleCrewInMovieList.adapter = movieCrewAdapter


        val tvCastAdapter = PeopleCastAdapter(mutableListOf(), object : (TmdbCombinedCast) -> Unit {
            override fun invoke(p1: TmdbCombinedCast) {
                val intent = Intent(this@PeopleDetailActivity, TvDetailActivity::class.java)
                intent.putExtra("tvId", p1.id)
                startActivity(intent)
            }
        })
        binding.peopleCastInTvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.peopleCastInTvList.adapter = tvCastAdapter


        val tvCrewAdapter = PeopleCrewAdapter(mutableListOf(), object : (TmdbCombinedCrew) -> Unit {
            override fun invoke(p1: TmdbCombinedCrew) {
                val intent = Intent(this@PeopleDetailActivity, TvDetailActivity::class.java)
                intent.putExtra("tvId", p1.id)
                startActivity(intent)
            }
        })
        binding.peopleCrewInTvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.peopleCrewInTvList.adapter = tvCrewAdapter



        viewModel.fetchPeopleDetailData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.peopleDetail != null) {

                        binding.peoplePoster.load(
                            Utils.getImageFullUrl(
                                it.peopleDetail.profile_path ?: ""
                            )
                        ) {
                            transformations(RoundedCornersTransformation(12f))
                        }

                        binding.peopleName.text = it.peopleDetail.name
                        binding.peopleBirthdayValue.text = it.peopleDetail.birthday
                        binding.peopleBirthplaceValue.text = it.peopleDetail.place_of_birth
                        binding.peopleOverview.text = it.peopleDetail.biography
                        binding.peopleSexValue.text =
                            if (it.peopleDetail.gender == 1) "Male" else "Female"
                        binding.peopleNicknameValue.text =
                            it.peopleDetail.also_known_as.joinToString(",")

                    }
                    if (it.peopleMovieCast != null && it.peopleMovieCast.size > 0) {
                        movieCastAdapter.updateData(it.peopleMovieCast)
                        binding.peopleCastInMovieTitle.visibility = View.VISIBLE
                        binding.peopleCastInMovieList.visibility = View.VISIBLE
                    } else {
                        binding.peopleCastInMovieTitle.visibility = View.GONE
                        binding.peopleCastInMovieList.visibility = View.GONE
                    }


                    if (it.peopleMovieCrew != null && it.peopleMovieCrew.size > 0) {
                        movieCrewAdapter.updateData(it.peopleMovieCrew)
                        binding.peopleCrewInMovieTitle.visibility = View.VISIBLE
                        binding.peopleCrewInMovieList.visibility = View.VISIBLE
                    } else {
                        binding.peopleCrewInMovieTitle.visibility = View.GONE
                        binding.peopleCrewInMovieList.visibility = View.GONE
                    }


                    if (it.peopleTvCrew != null && it.peopleTvCrew.size > 0) {
                        tvCrewAdapter.updateData(it.peopleTvCrew)
                        binding.peopleCrewInTvTitle.visibility = View.VISIBLE
                        binding.peopleCrewInTvList.visibility = View.VISIBLE
                    } else {
                        binding.peopleCrewInTvTitle.visibility = View.GONE
                        binding.peopleCrewInTvList.visibility = View.GONE
                    }


                    if (it.peopleTvCast != null && it.peopleTvCast.size > 0) {
                        tvCastAdapter.updateData(it.peopleTvCast)
                        binding.peopleCastInTvTitle.visibility = View.VISIBLE
                        binding.peopleCastInTvList.visibility = View.VISIBLE
                    } else {
                        binding.peopleCastInTvTitle.visibility = View.GONE
                        binding.peopleCastInTvList.visibility = View.GONE
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