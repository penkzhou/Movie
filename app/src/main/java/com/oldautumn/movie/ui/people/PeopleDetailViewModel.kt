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
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val peopleId = savedStateHandle.get("peopleId") ?: 0

    private val _uiState = MutableStateFlow(
        PeopleDetailUiState(
            null,
            null,
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            0,
            "",
        ),

    )
    val uiState: StateFlow<PeopleDetailUiState> = _uiState.asStateFlow()

    private var fetchPeopleDetailJob: Job? = null
    private var fetchPeopleCreditJob: Job? = null
    private var fetchPeopleImageJob: Job? = null
    private var fetchTraktPeopleDetailJob: Job? = null

    fun fetchPeopleDetailData() {
        if (peopleId > 0) {
            fetchPeopleDetail(peopleId)
            fetchPeopleCredit(peopleId)
            fetchPeopleImage(peopleId)
        }
    }

    private fun fetchPeopleDetail(peopleId: Int) {
        fetchPeopleDetailJob?.cancel()
        fetchPeopleDetailJob = viewModelScope.launch {
            try {
                val peopleDetail = repository.getPeopleDetail(peopleId)
                _uiState.value = _uiState.value.copy(peopleDetail = peopleDetail)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }
        }
    }

    private fun fetchPeopleCredit(peopleId: Int) {
        fetchPeopleCreditJob?.cancel()
        fetchPeopleCreditJob = viewModelScope.launch {
            try {
                val movieCredit = repository.getPeopleCredit(peopleId)
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
                    peopleTvCrew = tvCrewList,
                )
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }
        }
    }

    private fun fetchPeopleImage(peopleId: Int) {
        fetchPeopleImageJob?.cancel()
        fetchPeopleImageJob = viewModelScope.launch {
            try {
                val peopleImage = repository.getPeopleImage(peopleId)
                val peopleImageList =
                    peopleImage.profiles.subList(0, minOf(7, peopleImage.profiles.size))
                val peopleImageSize = peopleImage.profiles.size
                _uiState.value = _uiState.value.copy(
                    peopleImageList = peopleImageList.toMutableList(),
                    peopleImageSize = peopleImageSize,
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
