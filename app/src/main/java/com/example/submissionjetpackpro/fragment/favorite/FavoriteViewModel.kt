package com.example.submissionjetpackpro.fragment.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MoviesUseCase

class FavoriteViewModel(moviesUseCase: MoviesUseCase): ViewModel() {
    val favoriteMovies = moviesUseCase.getFavoriteMovies().asLiveData()
}