package com.example.submissionjetpackpro.fragment.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MoviesUseCase

class MoviesViewModel(moviesUseCase: MoviesUseCase): ViewModel() {
    val movies = moviesUseCase.getAllMovies().asLiveData()
}