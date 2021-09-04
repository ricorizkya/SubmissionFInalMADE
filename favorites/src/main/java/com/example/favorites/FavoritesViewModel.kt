package com.example.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MoviesUseCase

class FavoritesViewModel(moviesUseCase: MoviesUseCase): ViewModel() {
    val favorites = moviesUseCase.getFavoriteMovies().asLiveData()
}