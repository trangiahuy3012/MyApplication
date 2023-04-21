package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.MySharedPreferences
import com.example.myapplication.data.DataStore
import com.example.myapplication.database.Account
import com.example.myapplication.database.AccountDatabase
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignUpViewModel(val prefs: MySharedPreferences, val db: AccountDatabase) : ViewModel() {
    val fullName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private var _isErrorEvent: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorEvent: LiveData<Boolean>
        get() = _isErrorEvent

    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    fun registerUser(email: String, pass: String) {
        prefs.saveEmail(email)
        prefs.savePassword(pass)
        _isSuccessEvent.value = true;
    }

    fun registerUserWithDB(username: String, email: String, pass: String) {
        viewModelScope.launch {
            val account = db.accountDao().checkUsernameExisting(username)
            if(account == null) {
                db.accountDao()
                    .insert(Account(username = username, email = email, password = pass))
                _isSuccessEvent.value = true;
            } else {
                _isErrorEvent.value = true;
            }
        }
    }

}