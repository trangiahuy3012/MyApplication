package com.example.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.MySharedPreferences
import com.example.myapplication.app.Event
import com.example.myapplication.data.DataStore
import com.example.myapplication.database.AccountDatabase
import kotlinx.coroutines.launch

class SignInViewModel(val prefs: MySharedPreferences, val db: AccountDatabase) :
    ViewModel() {

    private var _loginSuccessEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()
    val saveSuccessEvent: LiveData<Event<Boolean>>
        get() = _loginSuccessEvent

    private var _loginFailedEvent: MutableLiveData<String> = MutableLiveData()
    val loginFailedEvent: LiveData<String>
        get() = _loginFailedEvent


    fun login(user: String, password: String) {
        val storedUsername = prefs.getUsername()
        val storePassword = prefs.getPassword()
        if (user.isEmpty() || storedUsername != user) {
            _loginSuccessEvent.value = Event(false)
            _loginFailedEvent.value = "Tên đăng nhập không hợp lệ"
            return;
        }
        if (password.isEmpty() || storePassword != password) {
            _loginSuccessEvent.value = Event(false)
            _loginFailedEvent.value = "Mật khẩu không hợp lệ"
            return;
        }
        Log.e("success", "login success")
        _loginSuccessEvent.value = Event(true)
    }

    fun loginWithDatabase(email: String, password: String) {
        if (email.isEmpty()) {
            _loginSuccessEvent.value = Event(false)
            _loginFailedEvent.value = "Email đăng nhập không hợp lệ"
            return;
        }
        if (password.isEmpty() ) {
            _loginSuccessEvent.value = Event(false)
            _loginFailedEvent.value = "Mật khẩu không hợp lệ"
            return;
        }
        Log.e("success", "login success")
        viewModelScope.launch {
            val account = db.accountDao().searchAccount(email = email,
                pass = password)
            if (account != null) {
                _loginSuccessEvent.value = Event(true)
            } else {
                _loginSuccessEvent.value = Event(false)
                _loginFailedEvent.value = "Tài Khoản Không Tồn Tại"
            }

        }

    }

}



