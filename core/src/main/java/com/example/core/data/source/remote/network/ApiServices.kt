package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    companion object {
        const val API_KEY = "4243d3b293cb75eba91f309e62952dc3"
    }

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getListMovies(
        @Query("page") page: Int
    ): MoviesResponse
}