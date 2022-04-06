package com.oldautumn.movie.ui.people

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.media.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PeopleDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val peopleId = savedStateHandle.get("peopleId") ?: 0

    private val _uiState = MutableStateFlow(
        PeopleDetailUiState(
            null,
            null,
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            "",
        )

    )
    val uiState: StateFlow<PeopleDetailUiState> = _uiState.asStateFlow()


    private var fetchPeopleDetailJob: Job? = null
    private var fetchPeopleCreditJob: Job? = null
    private var fetchTraktPeopleDetailJob: Job? = null

    fun fetchPeopleDetailData() {
        if (peopleId > 0) {
            fetchPeopleDetail(peopleId)
            fetchPeopleCredit(peopleId)
        }
    }

    private fun fetchPeopleDetail(people: Int) {
        fetchPeopleDetailJob?.cancel();
        fetchPeopleDetailJob = viewModelScope.launch {
            try {
                val peopleDetail = repository.getPeopleDetail(people)
                _uiState.value = _uiState.value.copy(peopleDetail = peopleDetail)

            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }


    private fun fetchPeopleCredit(people: Int) {
        fetchPeopleCreditJob?.cancel();
        fetchPeopleCreditJob = viewModelScope.launch {
            try {
                val movieCredit = repository.getPeopleCredit(people)
                val movieCrewList =
                    movieCredit.crew.filter { it.media_type == "movie" }.toMutableList()
                val movieCastList =
                    movieCredit.cast.filter { it.media_type == "movie" }.toMutableList()
                val tvCrewList = movieCredit.crew.filter { it.media_type == "tv" }.toMutableList()
                val tvCastList = movieCredit.cast.filter { it.media_type == "tv" }.toMutableList()
                _uiState.value = _uiState.value.copy(
                    peopleMovieCast = movieCastList,
                    peopleMovieCrew = movieCrewList,
                    peopleTvCast = tvCastList,
                    peopleTvCrew = tvCrewList
                )
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }


    override fun onCleared() {
        savedStateHandle["peopleId"] = peopleId
        super.onCleared()
    }

}