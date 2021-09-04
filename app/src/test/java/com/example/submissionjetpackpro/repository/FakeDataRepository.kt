package com.example.submissionjetpackpro.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.submissionjetpackpro.core.data.NetworkBoundResources
import com.example.submissionjetpackpro.data.local.GetLocalDataSource
import com.example.submissionjetpackpro.core.data.source.local.entity.MoviesEntity
import com.example.submissionjetpackpro.core.data.source.remote.RemoteDataSource
import com.example.submissionjetpackpro.data.local.model.TVShowsEntity
import com.example.submissionjetpackpro.data.remote.*
import com.example.submissionjetpackpro.core.data.source.remote.response.MoviesDataResponse
import com.example.submissionjetpackpro.core.utils.AppExecutors
import com.example.submissionjetpackpro.data.remote.tvshows.TVShowsDataResponse
import com.example.submissionjetpackpro.vo.Resources

class FakeDataRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: GetLocalDataSource,
        private val appExecutors: AppExecutors): GetDataSource {

    /**MOVIES**/
    override fun movies(): LiveData<Resources<List<MoviesEntity>>> {
        return object : NetworkBoundResources<List<MoviesEntity>, List<MoviesDataResponse>>(appExecutors) {
            override fun loadDB(): LiveData<List<MoviesEntity>> {
                return localDataSource.getMovies()
            }

            override fun fetch(resultType: List<MoviesEntity>?): Boolean {
                return resultType == null || resultType.isEmpty()
            }

            override fun apiCall(): LiveData<ApiResponse<List<MoviesDataResponse>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun callResult(requestType: List<MoviesDataResponse>?) {
                val movies = ArrayList<MoviesEntity>()
                if (requestType != null) {
                    for (moviesResponse in requestType) {
                        moviesResponse.apply {
                            val dataMovies = MoviesEntity(id, title, description, timeRelease, rating, imgPhoto)
                            movies.add(dataMovies)
                        }
                    }
                    localDataSource.insertMovies(movies)
                }
            }
        }.asFlow()
    }

    override fun moviesDetail(moviesId: Int): LiveData<Resources<MoviesEntity>> {
        return object : NetworkBoundResources<MoviesEntity, MoviesDataResponse>(appExecutors) {
            override fun loadDB(): LiveData<MoviesEntity> {
                return localDataSource.getMoviesDetail(moviesId)
            }

            override fun fetch(resultType: MoviesEntity?): Boolean {
                return resultType == null
            }

            override fun apiCall(): LiveData<ApiResponse<MoviesDataResponse>> {
                return remoteDataSource.getMoviesDetail(moviesId)
            }

            override fun callResult(requestType: MoviesDataResponse?) {

            }
        }.asFlow()
    }

    override fun moviesInsert(moviesEntity: List<MoviesEntity>) {
        val insert = {
            if (localDataSource.getMovies().value.isNullOrEmpty()) {
                localDataSource.insertMovies(moviesEntity)
            }
        }
        appExecutors.diskIO().execute(insert)
    }

    override fun setMoviesFavorite(moviesEntity: MoviesEntity, favorite: Boolean) {
        return appExecutors.diskIO().execute {
            localDataSource.setMoviesFavorite(moviesEntity, favorite)
        }
    }

    override fun getMoviesFavorite(): LiveData<PagedList<MoviesEntity>> {
        val favorite = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(20)
                .build()
        return LivePagedListBuilder(localDataSource.getMoviesFavorite(), favorite).build()
    }


    /**TV SHOWS**/
    override fun tvShows(): LiveData<Resources<List<TVShowsEntity>>> {
        return object : NetworkBoundResources<List<TVShowsEntity>, List<TVShowsDataResponse>>(appExecutors) {
            override fun loadDB(): LiveData<List<TVShowsEntity>> {
                return localDataSource.getTVShows()
            }

            override fun fetch(resultType: List<TVShowsEntity>?): Boolean {
                return resultType == null || resultType.isEmpty()
            }

            override fun apiCall(): LiveData<ApiResponse<List<TVShowsDataResponse>>> {
                return remoteDataSource.getTVShows()
            }

            override fun callResult(requestType: List<TVShowsDataResponse>?) {
                val tvShows = ArrayList<TVShowsEntity>()
                if (requestType != null) {
                    for (tvShowsResponse in requestType) {
                        tvShowsResponse.apply {
                            val dataTVShows = TVShowsEntity(id, title, description, timeRelease, rating, imgPhoto)
                            tvShows.add(dataTVShows)
                        }
                    }
                    localDataSource.insertTVShows(tvShows)
                }
            }
        }.asFlow()
    }

    override fun tvShowsDetail(tvShowsId: Int): LiveData<Resources<TVShowsEntity>> {
        return object : NetworkBoundResources<TVShowsEntity, TVShowsDataResponse>(appExecutors) {
            override fun loadDB(): LiveData<TVShowsEntity> {
                return localDataSource.getTVShowsDetail(tvShowsId)
            }

            override fun fetch(resultType: TVShowsEntity?): Boolean {
                return resultType == null
            }

            override fun apiCall(): LiveData<ApiResponse<TVShowsDataResponse>> {
                return remoteDataSource.getTVShowsDetail(tvShowsId)
            }

            override fun callResult(requestType: TVShowsDataResponse?) {

            }
        }.asFlow()
    }

    override fun tvShowsInsert(tvShowsEntity: List<TVShowsEntity>) {
        val insert = {
            if (localDataSource.getTVShows().value.isNullOrEmpty()) {
                localDataSource.insertTVShows(tvShowsEntity)
            }
        }
        appExecutors.diskIO().execute(insert)
    }

    override fun setTVShowsFavorite(tvShowsEntity: TVShowsEntity, favorite: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setTVShowsFavorite(tvShowsEntity, favorite)
        }
    }

    override fun getTVShowsFavorite(): LiveData<PagedList<TVShowsEntity>> {
        val favorite = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(20)
                .build()
        return LivePagedListBuilder(localDataSource.getTVShowsFavorite(), favorite).build()
    }


}