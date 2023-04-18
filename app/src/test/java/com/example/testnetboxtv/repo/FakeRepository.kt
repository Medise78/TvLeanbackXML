package com.example.testnetboxtv.repo

import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.domain.model.genres.GenresDto
import com.example.testnetboxtv.domain.model.genres_movies.GenresMoviesDto
import com.example.testnetboxtv.domain.model.movie_detail.MovieDetailDto
import com.example.testnetboxtv.domain.repository.MovieRepository

class FakeRepository : MovieRepository {
    override suspend fun getMovieById(id: Int): Resource<MovieDetailDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getGenres(): Resource<GenresDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesByGenres(genreId: Int, page: Int): Resource<GenresMoviesDto> {
        TODO("Not yet implemented")
    }

}