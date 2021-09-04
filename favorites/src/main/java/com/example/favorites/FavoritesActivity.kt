package com.example.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.MoviesAdapter
import com.example.favorites.databinding.ActivityFavoritesBinding
import com.example.submissionjetpackpro.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoritesModule)
        val moviesAdapter = MoviesAdapter()
        moviesAdapter.onItemClickCallback = { favorite ->
            Intent(this, DetailMovieActivity::class.java).also {
                it.putExtra(DetailMovieActivity.EXTRA_MOVIE, favorite)
                startActivity(it)
            }
        }

        viewModel.favorites.observe(this, {
            moviesAdapter.setMovies(it)
        })

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }
}