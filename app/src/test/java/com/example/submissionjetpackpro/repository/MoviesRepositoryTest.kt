package com.example.submissionjetpackpro.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submissionjetpackpro.data.local.GetLocalDataSource
import com.example.submissionjetpackpro.core.data.source.local.entity.MoviesEntity
import com.example.submissionjetpackpro.data.local.model.TVShowsEntity
import com.example.submissionjetpackpro.core.utils.AppExecutors
import com.example.submissionjetpackpro.core.data.source.remote.RemoteDataSource
import com.example.submissionjetpackpro.utils.DataDummy
import com.example.submissionjetpackpro.utils.LiveDataTest
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(GetLocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val dataRepository = FakeDataRepository(remote, local, appExecutors)

    /**MOVIES**/
    private val moviesRemote = DataDummy.getMoviesRemote()
    private val moviesId = moviesRemote[0].id
    private val moviesDataResources = Mockito.mock(androidx.paging.DataSource.Factory::class.java) as androidx.paging.DataSource.Factory<Int, MoviesEntity>

    /**TV SHOWS**/
    private val tvShowsRemote = DataDummy.getTVShowsRemote()
    private val tvShowsId = tvShowsRemote[0].id
    private val tvShowsDataResources = Mockito.mock(androidx.paging.DataSource.Factory::class.java) as androidx.paging.DataSource.Factory<Int, TVShowsEntity>

    @Test
    fun getListMovies() {
        val dummyMovies = MutableLiveData<List<MoviesEntity>>()
        dummyMovies.value = DataDummy.getMovies()
        `when`(local.getMovies()).thenReturn(dummyMovies)

        val moviesEntity = LiveDataTest.getValue(dataRepository.movies())
        verify(local).getMovies()
        assertNotNull(moviesEntity.data)
    }

    @Test
    fun getDetailMovies() {
        val dummyMoviesDetail = MutableLiveData<MoviesEntity>()
        dummyMoviesDetail.value = DataDummy.getMoviesDetail(moviesId)
        `when`(local.getMoviesDetail(moviesId)).thenReturn(dummyMoviesDetail)

        val detailMoviesEntity = LiveDataTest.getValue(dataRepository.moviesDetail(moviesId))
        verify(local).getMoviesDetail(moviesId)
        assertNotNull(detailMoviesEntity.data)
    }

    @Test
    fun getFavoriteMovies() {
        `when`(local.getMoviesFavorite()).thenReturn(moviesDataResources)
        dataRepository.getMoviesFavorite()
        verify(local).getMoviesFavorite()
    }

    @Test
    fun getListTVShows() {
        val dummyTVShows = MutableLiveData<List<TVShowsEntity>>()
        dummyTVShows.value = DataDummy.getTVShows()
        `when`(local.getTVShows()).thenReturn(dummyTVShows)

        val tvShowsEntity = LiveDataTest.getValue(dataRepository.tvShows())
        verify(local).getTVShows()
        assertNotNull(tvShowsEntity)
    }

    @Test
    fun getDetailTVShows() {
        val dummyDetailTVShows = MutableLiveData<TVShowsEntity>()
        dummyDetailTVShows.value = DataDummy.getTVShowsDetail(tvShowsId)
        `when`(local.getTVShowsDetail(tvShowsId)).thenReturn(dummyDetailTVShows)

        val detailTVShowsEntity = LiveDataTest.getValue(dataRepository.tvShowsDetail(tvShowsId))
        verify(local).getTVShowsDetail(tvShowsId)
        assertNotNull(detailTVShowsEntity)
    }

    @Test
    fun getFavoriteTVShows() {
        `when`(local.getTVShowsFavorite()).thenReturn(tvShowsDataResources)
        dataRepository.getTVShowsFavorite()
        verify(local).getTVShowsFavorite()
    }

}