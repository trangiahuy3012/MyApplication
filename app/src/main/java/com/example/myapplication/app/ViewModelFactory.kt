package com.example.myapplication.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MySharedPreferences
import com.example.myapplication.database.AccountDatabase
import com.example.myapplication.viewModels.SignInViewModel
import com.example.myapplication.viewModels.SignUpViewModel

class ViewModelFactory(val prefs: MySharedPreferences, val accountDatabase: AccountDatabase) :
    ViewModelProvider
.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            //val prefs = app.prefs
            return SignInViewModel(prefs, accountDatabase) as T
        } else if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            //val prefs = app.prefs
            return SignUpViewModel(prefs, accountDatabase) as T
        }
        throw IllegalArgumentException("unknown view model")
    }
}