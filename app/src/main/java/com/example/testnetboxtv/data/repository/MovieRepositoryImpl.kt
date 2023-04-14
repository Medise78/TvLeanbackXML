package com.example.testnetboxtv.data.repository

import com.example.testnetboxtv.common.Resource
import com.example.testnetboxtv.data.remote.ApiService
import com.example.testnetboxtv.domain.model.genres.GenresDto
import com.example.testnetboxtv.domain.model.genres_movies.GenresMoviesDto
import com.example.testnetboxtv.domain.model.movie_detail.MovieDetailDto
import com.example.testnetboxtv.domain.repository.MovieRepository
import retrofit2.HttpException
import java.security.cert.CertificateException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {
    override suspend fun getMovieById(id: Int): Resource<MovieDetailDto> {
        var requestResult: Resource<MovieDetailDto> = Resource.Loading()
        try {
            val response = apiService.getMoviesById(id)
            val successBody = response.body()
            if (successBody != null) {
                if (response.isSuccessful) {
                    requestResult = Resource.Success(successBody)
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    requestResult = try {
                        Resource.Error(errorBody.toString())
                    } catch (e: Exception) {
                        Resource.Error(e.message ?: "Error")
                    }
                }
            }
        } catch (e: HttpException) {
            requestResult = Resource.Error(e.message ?: "Error")
        } catch (e: Exception) {
            requestResult = if (e.cause != null) {
                if (e.cause is CertificateException) {
                    Resource.Error(e.cause?.message.toString())
                } else {
                    Resource.Error(e.message ?: "")
                }
            } else {
                Resource.Error(e.message ?: "Error")
            }
        }
        return requestResult
    }

    override suspend fun getGenres(): Resource<GenresDto> {
        var requestResult: Resource<GenresDto> = Resource.Loading()
        try {
            val response = apiService.getMoviesGenre()
            val successBody = response.body()

            if (successBody != null) {
                if (response.isSuccessful) {
                    requestResult = Resource.Success(successBody)
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    requestResult = try {
                        Resource.Error(errorBody.toString())
                    } catch (e: Exception) {
                        Resource.Error(e.message ?: "Error")
                    }
                }
            }
        } catch (e: HttpException) {
            requestResult = Resource.Error(e.message ?: "Error")
        } catch (e: Exception) {
            requestResult = if (e.cause != null) {
                if (e.cause is CertificateException) {
                    Resource.Error(e.cause?.message.toString())
                } else {
                    Resource.Error(e.message ?: "")
                }
            } else {
                Resource.Error(e.message ?: "Error")
            }
        }
        return requestResult
    }

    override suspend fun getMoviesByGenres(genreId: Int, page: Int): Resource<GenresMoviesDto> {
        var requestResult: Resource<GenresMoviesDto> = Resource.Loading()
        try {
            val response = apiService.getGenresMovie(genreId, page)
            val successBody = response.body()

            if (successBody != null) {
                if (response.isSuccessful) {
                    requestResult = Resource.Success(successBody)
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    requestResult = try {
                        Resource.Error(errorBody.toString())
                    } catch (e: Exception) {
                        Resource.Error(e.message ?: "Error")
                    }
                }
            }
        } catch (e: HttpException) {
            requestResult = Resource.Error(e.message ?: "Error")
        } catch (e: Exception) {
            requestResult = if (e.cause != null) {
                Resource.Error(e.message.toString())
            } else {
                Resource.Error(e.message ?: "Error")
            }
        }
        return requestResult
    }
}