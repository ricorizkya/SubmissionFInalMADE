package com.example.core.domain.usecase

import com.example.core.domain.model.Movies
import com.example.core.domain.repository.IMoviesRepository

class MoviesInteractor(private val moviesRepository: IMoviesRepository): MoviesUseCase {

    override fun getAllMovies() = moviesRepository.getAllMovies()

    override fun getFavoriteMovies() = moviesRepository.getFavoriteMovies()

    override fun setFavoriteMovies(movies: Movies, state: Boolean) = moviesRepository.setFavoriteMovies(movies, state)
}