package com.example.submissionjetpackpro.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.submissionjetpackpro.R
import com.example.submissionjetpackpro.core.ui.FavoriteMoviesAdapter
import com.example.submissionjetpackpro.adapter.FavoriteTVShowsAdapter
import com.example.submissionjetpackpro.core.ui.MoviesAdapter
import com.example.submissionjetpackpro.adapter.TVShowsAdapter
import com.example.submissionjetpackpro.utils.DataDummy
import com.example.submissionjetpackpro.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val dummyMovies = DataDummy.getMovies()
    private val dummyTVShows = DataDummy.getTVShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rate)).check(matches(withText(dummyMovies[0].rating)))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTVShows() {
        onView(withId(R.id.tv_menu)).perform(click())
        onView(withId(R.id.rv_tvShows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTVShows.size))
        onView(withId(R.id.rv_tvShows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tvShow_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tvShow_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tvShow_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tvShow_rate)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun setMoviesFavorite() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(0, click()))
        onView(withId(R.id.btn_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyMovies[0].title)))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rate)).check(matches(withText(dummyMovies[0].rating)))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun setTVShowsFavorite() {
        onView(withId(R.id.tv_menu)).perform(click())
        onView(withId(R.id.rv_tvShows)).perform(RecyclerViewActions.actionOnItemAtPosition<TVShowsAdapter.TVShowViewHolder>(0, click()))
        onView(withId(R.id.btn_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tvShows)).perform(RecyclerViewActions.actionOnItemAtPosition<FavoriteTVShowsAdapter.FavoriteTVShowsViewHolder>(0, click()))
        onView(withId(R.id.tvShow_title)).check(matches(withText(dummyTVShows[0].title)))
        onView(withId(R.id.tvShow_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tvShow_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tvShow_rate)).check(matches(withText(dummyTVShows[0].rating)))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
    }
}