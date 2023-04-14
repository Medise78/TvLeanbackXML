package com.example.testnetboxtv.domain.repository

import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.domain.model.genres.GenresDto
import com.example.testnetboxtv.domain.model.genres_movies.GenresMoviesDto
import com.example.testnetboxtv.domain.model.movie_detail.MovieDetailDto

interface MovieRepository {

    suspend fun getMovieById(id: Int): Resource<MovieDetailDto>

    suspend fun getGenres(): Resource<GenresDto>

    suspend fun getMoviesByGenres(genreId: Int, page: Int): Resource<GenresMoviesDto>
}