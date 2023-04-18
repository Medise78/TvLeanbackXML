package com.example.testnetboxtv.di

import com.example.testnetboxtv.BuildConfig
import com.example.testnetboxtv.common.Utils
import com.example.testnetboxtv.data.remote.ApiService
import com.example.testnetboxtv.data.repository.MovieRepositoryImpl
import com.example.testnetboxtv.domain.repository.MovieRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideApiService(httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }
}