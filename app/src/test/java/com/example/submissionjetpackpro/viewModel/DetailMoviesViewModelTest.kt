package com.example.submissionjetpackpro.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submissionjetpackpro.core.data.source.local.entity.MoviesEntity
import com.example.submissionjetpackpro.core.data.MoviesRepository
import com.example.submissionjetpackpro.detail.DetailMoviesViewModel
import com.example.submissionjetpackpro.utils.DataDummy
import com.example.submissionjetpackpro.vo.Resources
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMoviesViewModelTest {

    private lateinit var detailMoviesViewModelTest: DetailMoviesViewModel
    private val dummyMoviesDetail = DataDummy.getMovies()[0]
    private val moviesId = dummyMoviesDetail.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observer: Observer<Resources<MoviesEntity>>

    @Before
    fun setUp() {
        detailMoviesViewModelTest = DetailMoviesViewModel(moviesRepository)
        detailMoviesViewModelTest.setMoviesDetail(moviesId)
    }

    @Test
    fun getMoviesDetail() {
        val dummyDetailMovies = Resources.success(DataDummy.getMoviesDetail(moviesId))
        val moviesEntity = MutableLiveData<Resources<MoviesEntity>>()
        moviesEntity.value = dummyDetailMovies
        `when`(moviesRepository.moviesDetail(moviesId)).thenReturn(moviesEntity)
        detailMoviesViewModelTest.getMoviesDetail.observeForever(observer)
        verify(moviesRepository).moviesDetail(moviesId)
        verify(observer).onChanged(dummyDetailMovies)
        assertEquals(detailMoviesViewModelTest.getMoviesDetail.value?.data?.title, dummyMoviesDetail.title)
        assertEquals(detailMoviesViewModelTest.getMoviesDetail.value?.data?.timeRelease, dummyMoviesDetail.timeRelease)
        assertEquals(detailMoviesViewModelTest.getMoviesDetail.value?.data?.description, dummyMoviesDetail.description)
        assertEquals(detailMoviesViewModelTest.getMoviesDetail.value?.data?.rating, dummyMoviesDetail.rating)
        assertEquals(detailMoviesViewModelTest.getMoviesDetail.value?.data?.imgPhoto, dummyMoviesDetail.imgPhoto)
    }

    @Test
    fun setFavorite() {
        val dummyFavoriteMovies = Resources.success(DataDummy.getMovies()[0])
        val moviesEntity = MutableLiveData<Resources<MoviesEntity>>()
        moviesEntity.value = dummyFavoriteMovies
        `when`(moviesRepository.moviesDetail(moviesId)).thenReturn(moviesEntity)
        detailMoviesViewModelTest.getMoviesDetail.observeForever(observer)
        verify(observer).onChanged(moviesEntity.value)
        detailMoviesViewModelTest.setFavorite()
        verify(moviesRepository).setMoviesFavorite(moviesEntity.value?.data as MoviesEntity, true)
    }
}