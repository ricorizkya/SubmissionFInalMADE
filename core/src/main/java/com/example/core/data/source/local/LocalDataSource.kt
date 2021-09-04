package com.example.core.data.source.local

import androidx.lifecycle.LiveData
import com.example.core.data.source.local.entity.MoviesEntity
import com.example.core.data.source.local.room.DAO
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: DAO) {

    /**MOVIES**/
    fun getMovies(): Flow<List<MoviesEntity>> = dao.getMovies()
    suspend fun insertMovies(moviesEntity: List<MoviesEntity>) = dao.insertMovies(moviesEntity)
    fun getMoviesFavorite(): Flow<List<MoviesEntity>> = dao.getMoviesFavorite()
    fun setMoviesFavorite(moviesEntity: MoviesEntity, favorite: Boolean) {
        moviesEntity.favorite = favorite
        dao.updateMovies(moviesEntity)
    }
}