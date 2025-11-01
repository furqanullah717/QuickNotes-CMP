package com.codewithfk.quickernotes.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.quickernotes.data.cache.DataStoreManager
import com.codewithfk.quickernotes.data.remote.ApiService
import com.codewithfk.quickernotes.data.remote.HttpClientFactory
import com.codewithfk.quickernotes.model.AuthRequest
import com.codewithfk.quickernotes.model.AuthResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val apiService = ApiService(HttpClientFactory.getHttpClient(),dataStoreManager)
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Normal)
    val uiState = _uiState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<AuthNavigation>()
    val navigationFlow = _navigationFlow.asSharedFlow()


    fun onErrorClick() {
        viewModelScope.launch {
            _uiState.value = AuthState.Normal
        }
    }

    fun onSuccessClick(email: String) {
        viewModelScope.launch {
            _navigationFlow.emit(AuthNavigation.NavigateToHome(email))
        }
    }

    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword = _confirmPassword.asStateFlow()


    fun onEmailUpdated(email: String) {
        _email.value = email
    }

    fun onPasswordUpdated(password: String) {
        _password.value = password
    }

    fun onConfirmPasswordUpdated(password: String) {
        _confirmPassword.value = password
    }

    fun signup() {
        viewModelScope.launch {
            val request = AuthRequest(email.value, password.value)
            _uiState.value = AuthState.Loading
            val result = apiService.signup(request)
            if (result.isSuccess) {
                _uiState.value = AuthState.Success(result.getOrNull()!!)
                result.getOrNull()?.let {
                    dataStoreManager.storeEmail(it.email)
                    dataStoreManager.storeUserId(it.userId)
                    dataStoreManager.storeRefreshToken(it.refreshToken)
                    dataStoreManager.storeToken(it.accessToken)
                }
            } else {
                _uiState.value = AuthState.Failure(result.exceptionOrNull()?.message.toString())
            }
        }
    }
}

sealed class AuthNavigation {
    class NavigateToHome(val email: String) : AuthNavigation()
}

sealed class AuthState {
    object Normal : AuthState()
    object Loading : AuthState()
    class Success(val response: AuthResponse) : AuthState()
    class Failure(val error: String) : AuthState()
}