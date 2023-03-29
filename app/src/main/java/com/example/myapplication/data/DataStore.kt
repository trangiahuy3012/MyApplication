package com.example.myapplication.data

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.MovieID

class DataStore {
    companion object {
        var userDataStore: MutableList<MutableMap<String, String>> = mutableListOf(mutableMapOf(
            "fullName" to "Tran Gia Huy",
            "email" to "username@gmail.com",
            "password" to "12345678",
            "phoneNumber" to "+84 39 634 3467"
        ))
        val currentUserData: MutableLiveData<MutableMap<String, String>> = MutableLiveData()

        val curMovieData: MutableLiveData<MovieID> = MutableLiveData()
    }
}
