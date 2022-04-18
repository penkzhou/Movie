package com.oldautumn.movie.ui.people

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oldautumn.movie.data.api.model.TmdbCombinedCast
import com.oldautumn.movie.data.api.model.TmdbCombinedCrew
import com.oldautumn.movie.data.api.model.TmdbImageItem
import com.oldautumn.movie.databinding.ActivityPeopleDetailBinding
import com.oldautumn.movie.databinding.ItemPeopleCreditBinding
import com.oldautumn.movie.databinding.ItemPeopleImageBinding
import com.oldautumn.movie.ui.base.setup
import com.oldautumn.movie.ui.movie.MovieDetailActivity
import com.oldautumn.movie.ui.tv.TvDetailActivity
import com.oldautumn.movie.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        val movieCastAdapter = binding.peopleCastInMovieList.setup(
            mutableListOf<TmdbCombinedCast>(),
            ItemPeopleCreditBinding::inflate,
            onItemClick = {
                val intent = Intent(this@PeopleDetailActivity, MovieDetailActivity::class.java)
                intent.putExtra("movieId", it.id)
                startActivity(intent)
            },
            { binding, cast ->
                if (cast.poster_path != null && cast.poster_path.isNotEmpty()) {
                    binding?.peopleCreditPoster?.visibility = View.VISIBLE
                    binding?.peopleCreditPosterName?.visibility = View.GONE
                    binding?.peopleCreditPoster?.load(Utils.getImageFullUrl(cast.poster_path)) {
                        transformations(
                            RoundedCornersTransformation(16f)
                        )
                    }
                } else {
                    binding?.peopleCreditPoster?.visibility = View.GONE
                    binding?.peopleCreditPosterName?.visibility = View.VISIBLE
                    binding?.peopleCreditPosterName?.text = cast.name
                }
                binding?.peopleCreditPoster?.contentDescription = cast.name
                binding?.peopleCreditName?.text = "扮演${cast.character}"
            },

            manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false),

            )

        val movieCrewAdapter = binding.peopleCrewInMovieList.setup(
            mutableListOf<TmdbCombinedCrew>(),
            ItemPeopleCreditBinding::inflate,
            onItemClick = {
                val intent = Intent(this@PeopleDetailActivity, MovieDetailActivity::class.java)
                intent.putExtra("movieId", it.id)
                startActivity(intent)
            },
            { binding, cast ->
                if (cast.poster_path != null && cast.poster_path.isNotEmpty()) {
                    binding?.peopleCreditPoster?.visibility = View.VISIBLE
                    binding?.peopleCreditPosterName?.visibility = View.GONE
                    binding?.peopleCreditPoster?.load(Utils.getImageFullUrl(cast.poster_path)) {
                        transformations(
                            RoundedCornersTransformation(16f)
                        )
                    }
                } else {
                    binding?.peopleCreditPoster?.visibility = View.GONE
                    binding?.peopleCreditPosterName?.visibility = View.VISIBLE
                    binding?.peopleCreditPosterName?.text = cast.title
                }
                binding?.peopleCreditPoster?.contentDescription = cast.title
                binding?.peopleCreditName?.text = "担任${cast.job}"
            },
            manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false),

            )


        val tvCastAdapter = binding.peopleCastInTvList.setup(
            mutableListOf<TmdbCombinedCast>(),
            ItemPeopleCreditBinding::inflate,
            onItemClick = {
                val intent = Intent(this@PeopleDetailActivity, TvDetailActivity::class.java)
                intent.putExtra("tvId", it.id)
                startActivity(intent)
            },
            { binding, cast ->
                if (cast.poster_path != null && cast.poster_path.isNotEmpty()) {
                    binding?.peopleCreditPoster?.visibility = View.VISIBLE
                    binding?.peopleCreditPosterName?.visibility = View.GONE
                    binding?.peopleCreditPoster?.load(Utils.getImageFullUrl(cast.poster_path)) {
                        transformations(
                            RoundedCornersTransformation(16f)
                        )
                    }
                } else {
                    binding?.peopleCreditPoster?.visibility = View.GONE
                    binding?.peopleCreditPosterName?.visibility = View.VISIBLE
                    binding?.peopleCreditPosterName?.text = cast.name
                }
                binding?.peopleCreditPoster?.contentDescription = cast.name
                binding?.peopleCreditName?.text = "扮演${cast.character}"
            },

            manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false),

            )


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


        // 人物相册
        val peopleImageAdapter = binding.peopleImageList.setup(
            mutableListOf<TmdbImageItem>(),
            ItemPeopleImageBinding::inflate,
            onItemClick = {
//                val intent = Intent(this@PeopleDetailActivity, TvDetailActivity::class.java)
//                intent.putExtra("tvId", it.id)
//                startActivity(intent)
            },
            { binding, imageItem ->
                if (imageItem.file_path != null && imageItem.file_path.isNotEmpty()) {
                    binding?.peopleImage?.load(Utils.getImageFullUrl(imageItem.file_path)) {
                        transformations(
                            RoundedCornersTransformation(16f)
                        )
                    }
                }
            },

            manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false),

            )

        binding.peopleImageList.adapter = peopleImageAdapter




        viewModel.fetchPeopleDetailData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.peopleDetail != null) {
                        title = it.peopleDetail.name
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
                            if (it.peopleDetail.gender == 2) "Male" else "Female"
                        binding.peopleNicknameValue.text =
                            it.peopleDetail.also_known_as.joinToString(",")

                    }
                    if (it.peopleMovieCast != null && it.peopleMovieCast.size > 0) {
                        movieCastAdapter.update(it.peopleMovieCast)
                        binding.peopleCastInMovieTitle.visibility = View.VISIBLE
                        binding.peopleCastInMovieList.visibility = View.VISIBLE
                    } else {
                        binding.peopleCastInMovieTitle.visibility = View.GONE
                        binding.peopleCastInMovieList.visibility = View.GONE
                    }


                    if (it.peopleMovieCrew != null && it.peopleMovieCrew.size > 0) {
                        movieCrewAdapter.update(it.peopleMovieCrew)
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
                        tvCastAdapter.update(it.peopleTvCast)
                        binding.peopleCastInTvTitle.visibility = View.VISIBLE
                        binding.peopleCastInTvList.visibility = View.VISIBLE
                    } else {
                        binding.peopleCastInTvTitle.visibility = View.GONE
                        binding.peopleCastInTvList.visibility = View.GONE
                    }

                    if (it.peopleImageList != null && it.peopleImageList.size > 0) {
                        peopleImageAdapter.update(it.peopleImageList)
                    }
                    if (it.peopleImageSize > 0) {
                        binding.peopleImageTitle.text = "个人相册(${it.peopleImageSize})"
                    } else {
                        binding.peopleImageTitle.text = "个人相册"
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