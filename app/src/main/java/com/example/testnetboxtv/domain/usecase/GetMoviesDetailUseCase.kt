package com.example.testnetboxtv.domain.usecase

import com.example.testnetboxtv.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun execute(id: Int) = repository.getMovieById(id)
}