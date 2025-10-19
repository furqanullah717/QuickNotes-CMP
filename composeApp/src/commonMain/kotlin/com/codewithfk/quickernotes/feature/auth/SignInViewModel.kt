package com.codewithfk.quickernotes.feature.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()


    fun onEmailUpdated(email: String) {
        _email.value = email
    }

    fun onPasswordUpdated(password: String) {
        _password.value = password
    }


    fun signIn() {

    }
}