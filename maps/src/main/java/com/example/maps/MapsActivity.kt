package com.example.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.core.data.Resource
import com.example.maps.databinding.ActivityMapsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Netflix"
        getMoviesData()
    }

    private fun getMoviesData() {
        mapsViewModel.movies.observe(this, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvMaps.text = "This is map of ${movies.data?.get(0)?.title}"
                    }
                    is Resource.Errorr -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = movies.message
                    }
                }
            }
        })
    }
}