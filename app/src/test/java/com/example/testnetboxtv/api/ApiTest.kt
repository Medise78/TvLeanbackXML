package com.example.testnetboxtv.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testnetboxtv.data.remote.ApiService
import com.example.testnetboxtv.domain.repository.MovieRepository
import com.example.testnetboxtv.domain.usecase.GetGenreUseCase
import com.example.testnetboxtv.domain.usecase.GetGenresMoviesUseCase
import com.example.testnetboxtv.presenter.ui.home_screen.HomeViewModel
import com.example.testnetboxtv.repo.FakeRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class ApiTest {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var repository: MovieRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    private lateinit var fakeRepository: FakeRepository

    private lateinit var getGenresMoviesUseCase: GetGenresMoviesUseCase
    private lateinit var getGenreUseCase: GetGenreUseCase

    @Before
    fun setup() {
        hiltRule.inject()
        fakeRepository = FakeRepository()
//        getGenreUseCase = GetGenreUseCase(fakeRepository)
//        viewModel = HomeViewModel(getGenresMoviesUseCase, getGenreUseCase)
    }

    @org.junit.Test
    fun `Test Movie ApiService`() {
        runBlocking {
            assertThat(apiService.getMoviesGenre().body()).isEqualTo(repository.getGenres().data)
        }
    }

    @org.junit.Test
    fun `Test Movie By Id ApiService`() {
        runBlocking {
            assertThat(
                apiService.getGenresMovie(1, 1).body()
            ).isEqualTo(repository.getMoviesByGenres(1, 1).data)

        }
    }
    @org.junit.Test
    fun `Test GenresMovies ApiService`() {
        runBlocking {
            assertThat(
                apiService.getGenresMovie(1, 1).body()
            ).isEqualTo(repository.getMoviesByGenres(1, 1).data)
        }
    }
//    @org.junit.Test
//    fun `Test HomeViewModel`() {
//        runBlocking {
//            assertThat(
//                viewModel.fetchGenresMoviesMap()
//            ).isNotNull()
//        }
//    }
}