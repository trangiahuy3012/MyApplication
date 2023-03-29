package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.MovieID
import com.example.myapplication.data.DataStore

class MovieViewModel:ViewModel() {
    val curMovie: LiveData<MovieID>
        get() = DataStore.curMovieData
}