package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.DataStore

class SignInViewModel: ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("username@gmail.com")
    val password: MutableLiveData<String> = MutableLiveData("12345678")

    private var _isErrorEvent: MutableLiveData<String> = MutableLiveData()
    val isErrorEvent: LiveData<String>
        get() = _isErrorEvent

    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    fun onLogin() {
        var _errorString = ""
        val foundUser = DataStore.userDataStore.find { userData -> userData["email"] == email.value }

        if (foundUser == null) {
            _errorString += "Invalid Email or Password"
        }
        else
        {
            if(password.value != foundUser["password"])
                _errorString += "Invalid Password"
        }

        if(_errorString.isNotEmpty())
            return _isErrorEvent.postValue(_errorString)

        if (foundUser != null) {
            DataStore.currentUserData.value = foundUser
        }

        _isSuccessEvent.postValue(true)
    }
}