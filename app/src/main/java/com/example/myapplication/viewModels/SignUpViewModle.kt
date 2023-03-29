package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.DataStore
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    val fullName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private var _isErrorEvent: MutableLiveData<String> = MutableLiveData()
    val isErrorEvent: LiveData<String>
        get() = _isErrorEvent

    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    companion object {
        private val PASSWORD_PATTERN:Pattern = Pattern.compile(
            "^" +                    // start-of-string
                    "(?=.*[0-9])" +             // # digit must occur at least once
                    "(?=.*[a-z])" +            // a lower case letter must occur at least once
                    "(?=.*[A-Z])" +             // an upper case letter must occur at least once
                    "(?=.*[!@#$%^&*()])" +       // a special character must occur at least once you can replace with your special characters
                    ".{8,}" +                   // at least 8 characters
                    "$")                        // end-of-string
    }
    fun onSignUp() {

        var _errorString = ""

        // check fullName
        if (fullName.value.isNullOrEmpty())
            _errorString += "Your full name is null or empty"

        // check email
        if (!isEmailValid(email.value.toString()))
        {
            if(_errorString.isNotEmpty()) _errorString += "\n"
            _errorString += "Invalid E-mail"
        }

        if(isExistEmail(email.value.toString()))
        {
            if(_errorString.isNotEmpty()) _errorString += "\n"
            _errorString += "E-mail is already exist"
        }
        // check password
        if (!isPasswordValid(password.value.toString()))
        {
            if(_errorString.isNotEmpty()) _errorString += "\n"
            _errorString += "\nPassword "
        }

        // Throw Error
        if(_errorString.isNotEmpty())
            return _isErrorEvent.postValue(_errorString)

        _isSuccessEvent.postValue(true)

    }

    private fun isExistEmail(email: String): Boolean {
        val _isExistEmail = DataStore.userDataStore.find { userData -> userData["email"] == email }?.isNotEmpty()
        return _isExistEmail == true
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return PASSWORD_PATTERN.matcher(password).matches()
    }

}