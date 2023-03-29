package com.example.myapplication.viewModels

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.DataStore
import com.example.myapplication.data.Movie
import com.example.myapplication.R
import com.example.myapplication.services.MovieRestClient

import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _newList: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    val newList: LiveData<MutableList<Movie>>
        get() = _newList

    private val _menuLayoutType: MutableLiveData<Int> = MutableLiveData(R.id.menu_item_linear)
    val menuLayoutType: LiveData<Int>
        get() = _menuLayoutType

    private val _tabID: MutableLiveData<Int> = MutableLiveData(R.id.new_playing)
    val tabID: LiveData<Int>
        get() = _tabID

    fun getLayout(restaurantActivity: Activity): RecyclerView.LayoutManager {
        return when (_menuLayoutType.value) {
            R.id.menu_item_linear -> LinearLayoutManager(restaurantActivity)
            else -> {GridLayoutManager(restaurantActivity, 2)}
        }
    }

    fun setLayout(itemId: Int) {
        _menuLayoutType.postValue(itemId)
    }

    fun handleItemWhenClicked(item: View, movie: Movie, controller: NavController) {
        viewModelScope.launch {
            DataStore.curMovieData.postValue(MovieRestClient.getInstance().api.singleMovie(language = "en-US", id = movie.id))
            controller.navigate(R.id.action_homeFragment_to_movieFragment)
        }

    }

    fun handleItemWhenLongClicked(item: View) {
        /// TODO
    }

    private fun getNowPlayingMovie() {
        viewModelScope.launch {
            val movieResp = MovieRestClient.getInstance().api.listNowPlayMovies(language = "en-US", page = 1)
            _newList.postValue(movieResp.results.toMutableList())
        }
    }
    private fun getTopRatedMovie() {
        viewModelScope.launch {
            val movieResp = MovieRestClient.getInstance().api.listTopRatedMovies(language = "en-US", page = 1)
            _newList.postValue(movieResp.results.toMutableList())
        }
    }

    fun setTabID(itemId: Int) {
        _tabID.postValue(itemId)
    }

    fun getMovies() {
        when(_tabID.value){
            R.id.new_playing -> getNowPlayingMovie()
            else -> getTopRatedMovie()
        }
    }

}