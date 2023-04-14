package com.example.testnetboxtv.domain.model.genres


import com.google.gson.annotations.SerializedName

data class GenresDataDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = ""
)