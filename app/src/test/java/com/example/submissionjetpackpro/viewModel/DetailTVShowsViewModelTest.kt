package com.example.submissionjetpackpro.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submissionjetpackpro.data.local.model.TVShowsEntity
import com.example.submissionjetpackpro.core.data.MoviesRepository
import com.example.submissionjetpackpro.utils.DataDummy
import com.example.submissionjetpackpro.vo.Resources
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTVShowsViewModelTest {

    private lateinit var detailTVShowsViewModelTest: DetailTVShowsViewModel
    private val dummyTVShowsDetail = DataDummy.getTVShows()[0]
    private val tvShowsId = dummyTVShowsDetail.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observer: Observer<Resources<TVShowsEntity>>

    @Before
    fun setUp() {
        detailTVShowsViewModelTest = DetailTVShowsViewModel(moviesRepository)
        detailTVShowsViewModelTest.setTVShowsDetail(tvShowsId)
    }

    @Test
    fun getTVShowsDetail() {
        val dummyDetailTVShows = Resources.success(DataDummy.getTVShowsDetail(tvShowsId))
        val tvShowsEntity = MutableLiveData<Resources<TVShowsEntity>>()
        tvShowsEntity.value = dummyDetailTVShows
        `when`(moviesRepository.tvShowsDetail(tvShowsId)).thenReturn(tvShowsEntity)
        detailTVShowsViewModelTest.getTVShowsDetail.observeForever(observer)
        verify(moviesRepository).tvShowsDetail(tvShowsId)
        verify(observer).onChanged(dummyDetailTVShows)
        assertEquals(detailTVShowsViewModelTest.getTVShowsDetail.value?.data?.title, dummyTVShowsDetail.title)
        assertEquals(detailTVShowsViewModelTest.getTVShowsDetail.value?.data?.timeRelease, dummyTVShowsDetail.timeRelease)
        assertEquals(detailTVShowsViewModelTest.getTVShowsDetail.value?.data?.description, dummyTVShowsDetail.description)
        assertEquals(detailTVShowsViewModelTest.getTVShowsDetail.value?.data?.rating, dummyTVShowsDetail.rating)
        assertEquals(detailTVShowsViewModelTest.getTVShowsDetail.value?.data?.imgPhoto, dummyTVShowsDetail.imgPhoto)
    }

    @Test
    fun setFavorite() {
        val dummyFavoriteTVShows = Resources.success(DataDummy.getTVShows()[0])
        val tvShowsEntity = MutableLiveData<Resources<TVShowsEntity>>()
        tvShowsEntity.value = dummyFavoriteTVShows
        `when`(moviesRepository.tvShowsDetail(tvShowsId)).thenReturn(tvShowsEntity)
        detailTVShowsViewModelTest.getTVShowsDetail.observeForever(observer)
        verify(observer).onChanged(tvShowsEntity.value)
        detailTVShowsViewModelTest.setFavorite()
        verify(moviesRepository).setTVShowsFavorite(tvShowsEntity.value?.data as TVShowsEntity, true)
    }
}