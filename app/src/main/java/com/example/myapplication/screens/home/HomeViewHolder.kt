package com.example.myapplication.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.Movie
import com.example.myapplication.databinding.MovieItemViewBinding

class HomeViewHolder (private val binding: MovieItemViewBinding ): RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentRestaurantData: Movie
    private lateinit var callback: OnHomeItemClick
    init {
        binding.root.setOnClickListener{ callback.onClickItem(binding.root, currentRestaurantData) }
        binding.root.setOnLongClickListener {
            callback.onLongClick(binding.root)
            true
        }
    }

    fun bindData(movie: Movie, callback: OnHomeItemClick) {
        currentRestaurantData = movie
        this.callback = callback
        binding.moviedata = movie
        val urlImage = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(binding.root.context).load(urlImage).into(binding.restaurantAvt)
    }

    companion object {
        fun from(parent: ViewGroup): HomeViewHolder {
            val binding: MovieItemViewBinding = MovieItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HomeViewHolder(binding)
        }

    }
}