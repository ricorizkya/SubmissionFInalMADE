package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiServices
import com.example.core.data.source.remote.response.MoviesDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiServices: ApiServices) {

    suspend fun getAllMovies(): Flow<ApiResponse<List<MoviesDataResponse>>> {
        return flow {
            try {
                val response = apiServices.getListMovies(1)
                val dataArray = response.listMovie
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.listMovie))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
//        val movies = MutableLiveData<ApiResponse<List<MoviesDataResponse>>>()
//        val client = apiServices.getListMovies(1)
//        client.enqueue(object : Callback<MoviesResponse> {
//            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
//                val dataArray = response.body()?.listMovie
//                movies.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
//            }
//
//            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
//                movies.value = ApiResponse.Error(t.message.toString())
//                Log.e("RemoteDataSource", t.message.toString())
//            }
//        })
//        return movies
    }
}