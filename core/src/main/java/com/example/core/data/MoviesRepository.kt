package com.example.core.data

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.MoviesDataResponse
import com.example.core.domain.model.Movies
import com.example.core.domain.repository.IMoviesRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMoviesRepository {

    override fun getAllMovies(): Flow<Resource<List<Movies>>> =
            object : NetworkBoundResources<List<Movies>, List<MoviesDataResponse>>() {
                override fun loadDB(): Flow<List<Movies>> {
                    return localDataSource.getMovies().map {
                        DataMapper.mapEntitiesToDomain(it)
                    }
                }

                override fun fetch(resultType: List<Movies>?): Boolean =
                        resultType == null || resultType.isEmpty()

                override suspend fun apiCall(): Flow<ApiResponse<List<MoviesDataResponse>>> =
                        remoteDataSource.getAllMovies()

                override suspend fun callResult(requestType: List<MoviesDataResponse>) {
                    val listMovies = DataMapper.mapResponseToEntities(requestType)
                    localDataSource.insertMovies(listMovies)
                }

            }.asFlow()

    override fun getFavoriteMovies(): Flow<List<Movies>> {
        return localDataSource.getMoviesFavorite().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovies(movies: Movies, state: Boolean) {
        val moviesEntity = DataMapper.mapDomainToEntity(movies)
        appExecutors.diskIO().execute {
            localDataSource.setMoviesFavorite(moviesEntity, state)
        }
    }
}