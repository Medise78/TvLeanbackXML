package com.example.testnetboxtv.domain.model.genres_movies


import com.google.gson.annotations.SerializedName

data class GenresMoviesDto(
    @SerializedName("data")
    val `data`: List<GenresMovieData>,
    @SerializedName("metadata")
    val metadata: Metadata
)