package com.example.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MoviesUseCase

class MapsViewModel(moviesUseCase: MoviesUseCase): ViewModel() {
    val movies = moviesUseCase.getAllMovies().asLiveData()
}