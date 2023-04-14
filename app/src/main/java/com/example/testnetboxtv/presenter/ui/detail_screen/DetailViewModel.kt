package com.example.testnetboxtv.presenter.ui.detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.domain.model.movie_detail.MovieDetailDto
import com.example.testnetboxtv.domain.repository.MovieRepository
import com.example.testnetboxtv.domain.usecase.GetMoviesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMoviesDetailUseCase: GetMoviesDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState: MutableStateFlow<Resource<MovieDetailDto>> =
        MutableStateFlow(Resource.Loading())
    val detailState = _detailState.asStateFlow()


    init {
        savedStateHandle.get<Int>("id")?.let {
            fetchDetailData(it)
        }
    }

    private fun fetchDetailData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesDetailUseCase.execute(id).data?.let {
                try {
                    _detailState.tryEmit(
                        Resource.Success(it)
                    )
                } catch (e: Exception) {
                    _detailState.tryEmit(
                        Resource.Error(e.message ?: "Error")
                    )
                }
            }
        }
    }
}
