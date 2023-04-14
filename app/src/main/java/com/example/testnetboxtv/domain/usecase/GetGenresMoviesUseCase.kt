package com.example.testnetboxtv.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testnetboxtv.data.remote.ApiService
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import com.example.testnetboxtv.paging.VideoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGenresMoviesUseCase @Inject constructor(
    private val apiService: ApiService,
) {

    fun executeWithPaging(genreId: Int): Flow<PagingData<GenresMovieData>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                VideoPagingSource(apiService, genreId)
            }
        ).flow
}