package com.example.testnetboxtv.data.remote

import com.example.testnetboxtv.domain.model.genres.GenresDto
import com.example.testnetboxtv.domain.model.genres_movies.GenresMoviesDto
import com.example.testnetboxtv.domain.model.movie_detail.MovieDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movies/{movie_id}")
    suspend fun getMoviesById(
        @Path("movie_id") movieId:Int
    ):Response<MovieDetailDto>

    @GET("genres")
    suspend fun getMoviesGenre(): Response<GenresDto>

    @GET("genres/{genre_id}/movies?page=1")
    suspend fun getGenresMovie(
        @Path("genre_id") genreId: Int,
        @Query("page") page: Int
    ): Response<GenresMoviesDto>
}