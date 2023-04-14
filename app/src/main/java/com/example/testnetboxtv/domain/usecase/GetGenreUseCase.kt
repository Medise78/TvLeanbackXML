package com.example.testnetboxtv.domain.usecase

import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.domain.model.genres.GenresDto
import com.example.testnetboxtv.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGenreUseCase @Inject constructor(private val repository: MovieRepository){
   suspend fun execute():Resource<GenresDto> = repository.getGenres()
}