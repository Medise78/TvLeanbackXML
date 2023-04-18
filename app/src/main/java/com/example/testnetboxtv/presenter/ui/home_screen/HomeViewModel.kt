package com.example.testnetboxtv.presenter.ui.home_screen

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.domain.model.genres.GenresDataDto
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import com.example.testnetboxtv.domain.usecase.GetGenreUseCase
import com.example.testnetboxtv.domain.usecase.GetGenresMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresMoviesUseCase: GetGenresMoviesUseCase,
    private val getGenreUseCase: GetGenreUseCase,
) : ViewModel() {

    private val _genreMoviesMap: MutableStateFlow<Resource<MutableMap<GenresDataDto, Flow<PagingData<GenresMovieData>>>>> =
        MutableStateFlow(Resource.Loading())
    val genreMoviesMap: Flow<Resource<MutableMap<GenresDataDto, Flow<PagingData<GenresMovieData>>>>>
        get() = _genreMoviesMap

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun fetchGenresMoviesMap() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val genres = getGenreUseCase.execute()

                val updateGenreMoviesMap: MutableMap<GenresDataDto, Flow<PagingData<GenresMovieData>>> =
                    mutableMapOf()

                genres.data?.forEach {
                    updateGenreMoviesMap[it!!] = getGenresMoviesUseCase.executeWithPaging(it.id!!)
                }

                _genreMoviesMap.tryEmit(Resource.Success(updateGenreMoviesMap))
            } catch (e: Exception) {
                _genreMoviesMap.tryEmit(Resource.Error(e.message ?: "Error"))
            }
        }
    }
}