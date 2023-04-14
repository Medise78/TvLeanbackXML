package com.example.testnetboxtv.domain.model.genres_movies


import com.google.gson.annotations.SerializedName

data class GenresMovieData(
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("genres")
    val genres: List<String> = emptyList(),
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("images")
    val images: List<String> = emptyList(),
    @SerializedName("imdb_rating")
    val imdbRating: String = "",
    @SerializedName("poster")
    val poster: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("year")
    val year: String = ""
)