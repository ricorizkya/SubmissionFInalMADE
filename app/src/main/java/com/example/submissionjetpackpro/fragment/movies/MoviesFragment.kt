package com.example.submissionjetpackpro.fragment.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.MoviesAdapter
import com.example.submissionjetpackpro.detail.DetailMovieActivity
import com.example.submissionjetpackpro.databinding.FragmentMoviesBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val moviesAdapter = MoviesAdapter()
            moviesAdapter.onItemClickCallback = { selectedMovie ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedMovie)
                startActivity(intent)
            }


            moviesViewModel.movies.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading<*> -> {
                            binding.imgLoading.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success<*> -> {
                            binding.imgLoading.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            moviesAdapter.setMovies(movies.data)
                        }
                    }
                }
            })

            with(binding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }
}