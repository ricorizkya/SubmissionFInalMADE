package com.example.core.data.source.remote.response

import com.example.core.data.source.remote.response.genre.GenreResponse
import com.google.gson.annotations.SerializedName

data class MoviesDataResponse (

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val description: String,
    @SerializedName("genres")
    val genre: List<GenreResponse>,
    @SerializedName("release_date")
    val timeRelease: String,
    @SerializedName("vote_average")
    val rating: String,
    @SerializedName("poster_path")
    val imgPhoto: String
)
