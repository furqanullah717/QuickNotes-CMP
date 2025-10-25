package com.codewithfk.quickernotes.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.quickernotes.data.remote.ApiService
import com.codewithfk.quickernotes.data.remote.HttpClientFactory
import com.codewithfk.quickernotes.model.AuthRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val apiService = ApiService(HttpClientFactory.getHttpClient())

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Normal)
    val uiState = _uiState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<AuthNavigation>()
    val navigationFlow = _navigationFlow.asSharedFlow()



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
    fun onErrorButtonClick() {
        viewModelScope.launch {
            _uiState.value = AuthState.Normal
        }
    }

    fun onSuccessClick(){
        viewModelScope.launch {
            _navigationFlow.emit(AuthNavigation.NavigateToHome)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            val result = apiService.signIn(AuthRequest(email.value, password.value))
            if (result.isSuccess) {
                println(result.getOrNull())
                _uiState.value = AuthState.Success(result.getOrNull()!!)
            } else {
                println(result.exceptionOrNull())
                _uiState.value = AuthState.Failed(result.exceptionOrNull()!!.message.toString())
            }
        }
    }
}