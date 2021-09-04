package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Query("SELECT * FROM tb_movies")
    fun getMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM tb_movies WHERE favorite = 1")
    fun getMoviesFavorite(): Flow<List<MoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesEntity: List<MoviesEntity>)

    @Update
    fun updateMovies(moviesEntity: MoviesEntity)

}