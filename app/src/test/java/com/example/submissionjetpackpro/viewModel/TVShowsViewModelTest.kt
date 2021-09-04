package com.example.submissionjetpackpro.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submissionjetpackpro.data.local.model.TVShowsEntity
import com.example.submissionjetpackpro.core.data.MoviesRepository
import com.example.submissionjetpackpro.utils.DataDummy
import com.example.submissionjetpackpro.vo.Resources
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TVShowsViewModelTest {

    private lateinit var viewModel: TVShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observer: Observer<Resources<List<TVShowsEntity>>>

    @Before
    fun setUp() {
        viewModel = TVShowsViewModel(moviesRepository)
    }

    @Test
    fun getTVShows() {
        val dummyTVShows = Resources.success(DataDummy.getTVShows())
        val listTVShows = MutableLiveData<Resources<List<TVShowsEntity>>>()
        listTVShows.value = dummyTVShows

        `when`(moviesRepository.tvShows()).thenReturn(listTVShows)
        val dataTVShows = viewModel.getTVShows().value
        verify(moviesRepository).tvShows()
        assertNotNull(dataTVShows)

        viewModel.getTVShows().observeForever(observer)
        verify(observer).onChanged(dummyTVShows)
    }
}