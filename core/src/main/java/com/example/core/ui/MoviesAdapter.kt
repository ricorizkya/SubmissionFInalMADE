package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListBinding
import com.example.core.domain.model.Movies

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var onItemClickCallback: ((Movies) -> Unit)? = null
    private var listMovies = ArrayList<Movies>()

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemListBinding.bind(view)
        fun bind(movies: Movies) {
            with(binding) {
                tvTitle.text = movies.title
                tvDescription.text = movies.description
                tvRate.text = movies.rating
                Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w500" + movies.imgPhoto)
                        .into(imgPhoto)
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClickCallback?.invoke(listMovies[adapterPosition])
            }
        }
    }

    fun setMovies(movies: List<Movies>?) {
        if (movies == null) return
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size
}