package com.example.submissionjetpackpro.di

import com.example.core.domain.usecase.MoviesInteractor
import com.example.core.domain.usecase.MoviesUseCase
import com.example.submissionjetpackpro.detail.DetailMoviesViewModel
import com.example.submissionjetpackpro.fragment.favorite.FavoriteViewModel
import com.example.submissionjetpackpro.fragment.movies.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> {
        MoviesInteractor(get())
    }
}

val viewModelModule = module {
    viewModel {
        MoviesViewModel(get())
    }
    viewModel {
        FavoriteViewModel(get())
    }
    viewModel {
        DetailMoviesViewModel(get())
    }
}