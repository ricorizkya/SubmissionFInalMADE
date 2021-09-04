package com.example.submissionjetpackpro.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.Movies
import com.example.core.domain.usecase.MoviesUseCase

class DetailMoviesViewModel(private val moviesUseCase: MoviesUseCase): ViewModel() {

    fun setFavoriteMovies(movies: Movies, newStatus: Boolean) =
            moviesUseCase.setFavoriteMovies(movies, newStatus)
}