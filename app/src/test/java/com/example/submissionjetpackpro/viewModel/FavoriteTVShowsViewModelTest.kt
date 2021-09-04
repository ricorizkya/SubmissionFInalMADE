package com.example.submissionjetpackpro.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submissionjetpackpro.data.local.model.TVShowsEntity
import com.example.submissionjetpackpro.core.data.MoviesRepository
import com.nhaarman.mockitokotlin2.verify
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
class FavoriteTVShowsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<TVShowsEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TVShowsEntity>

    @Mock
    private var repository = Mockito.mock(MoviesRepository::class.java)

    private lateinit var viewModel: FavoriteTVShowsViewModel


    @Before
    fun setUp() {
        viewModel = FavoriteTVShowsViewModel(repository)
    }

    @Test
    fun getFavoriteTv() {
        val dummyTv = pagedList
        `when`(dummyTv.size).thenReturn(10)
        val tvs = MutableLiveData<PagedList<TVShowsEntity>>()
        tvs.value = dummyTv

        `when`(repository.getTVShowsFavorite()).thenReturn(tvs)
        val tvsEntities = viewModel.favoriteTVShows().value
        verify(repository).getTVShowsFavorite()
        Assert.assertNotNull(tvsEntities)
        Assert.assertEquals(10, tvsEntities?.size)

        viewModel.favoriteTVShows().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}