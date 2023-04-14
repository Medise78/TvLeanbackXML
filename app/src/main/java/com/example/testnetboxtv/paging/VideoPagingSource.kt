package com.example.testnetboxtv.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testnetboxtv.data.remote.ApiService
import com.example.testnetboxtv.domain.model.genres_movies.GenresMovieData
import com.example.testnetboxtv.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.ceil

const val PAGE_SIZE = 20

class VideoPagingSource(
    private val apiService: ApiService,
    private val genreId:Int,
) : PagingSource<Int, GenresMovieData>() {
    override fun getRefreshKey(state: PagingState<Int, GenresMovieData>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GenresMovieData> {
        try {
            val page = params.key ?: 1

            val response = apiService.getGenresMovie(genreId, page)

            val body = response.body()

            val videos = body?.data?.filter { it.id != null }.orEmpty()

            val prevKey = if (page > 1) page - 1 else null

            var nextKey: Int? = null

            nextKey = page + 1

            return LoadResult.Page(
                data = videos,
                prevKey, nextKey
            )
        }catch (e: IOException) {
            Log.d("loadStates", "LoadState.ErrorIO")
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("loadStates", "LoadState.ErrorHttpException")
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override val jumpingSupported: Boolean
        get() = true

    private fun getNumberOfPages(currentPage: Int, pageSize: Int = PAGE_SIZE) =
        ceil(currentPage.toFloat().div(pageSize.toFloat())).toInt()

}