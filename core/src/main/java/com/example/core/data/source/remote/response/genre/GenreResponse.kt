package com.example.core.data.source.remote.response.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse(

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String

)
