package com.example.myapplication.screens.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.viewModels.MovieViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : Fragment() {
    lateinit var binding: FragmentMovieBinding
    lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        binding.viewModel = viewModel

        val urlImage = "https://image.tmdb.org/t/p/w500${viewModel.curMovie.value?.backdropPath}"
        Glide.with(binding.root.context).load(urlImage).into(binding.imageViewBackDrop)

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MovieFragment().apply {
            }
    }
}