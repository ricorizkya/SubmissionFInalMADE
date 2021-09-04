package com.example.submissionjetpackpro.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submissionjetpackpro.core.data.source.local.entity.MoviesEntity
import com.example.submissionjetpackpro.core.data.MoviesRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<MoviesEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesEntity>

    @Mock
    private var repository = Mockito.mock(MoviesRepository::class.java)

    private lateinit var viewModel: FavoriteMoviesViewModel

    @Before
    fun setUp(){
        viewModel = FavoriteMoviesViewModel(repository)
    }

    @Test
    fun getFavoriteMovie() {
        val dummyFavoriteMovie = pagedList
        `when`(dummyFavoriteMovie.size).thenReturn(10)
        val favoriteMovie = MutableLiveData<PagedList<MoviesEntity>>()
        favoriteMovie.value = dummyFavoriteMovie

        `when`(repository.getMoviesFavorite()).thenReturn(favoriteMovie)
        val movieEntities = viewModel.favoriteMovies().value
        Mockito.verify(repository).getMoviesFavorite()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(10, movieEntities?.size)

        viewModel.favoriteMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyFavoriteMovie)
    }
}