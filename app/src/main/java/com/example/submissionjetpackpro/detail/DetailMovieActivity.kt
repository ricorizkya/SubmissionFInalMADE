package com.example.submissionjetpackpro.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.core.domain.model.Movies
import com.example.submissionjetpackpro.R
import com.example.submissionjetpackpro.databinding.ActivityDetailMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailMovies = intent.getParcelableExtra<Movies>(EXTRA_MOVIE)
        setDetailMovies(detailMovies)
    }

    private fun setDetailMovies(movies: Movies?) {
        movies?.let {
            with(binding) {
                binding.tvTitle.text = movies.title
                binding.tvDescription.text = movies.description
                binding.tvRate.text = movies.rating
                binding.tvRelease.text = movies.timeRelease
                Glide.with(this@DetailMovieActivity)
                        .load("https://image.tmdb.org/t/p/w500" + movies.imgPhoto)
                        .into(binding.imgPoster)

                var statusFavorite = movies.isFavorite
                favoriteState(statusFavorite)
                binding.btnFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    viewModel.setFavoriteMovies(movies, statusFavorite)
                    favoriteState(statusFavorite)
                }
            }
        }
    }

    private fun favoriteState(stateFavorite: Boolean) {
        if (stateFavorite) {
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
        }else {
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
        }
    }

}