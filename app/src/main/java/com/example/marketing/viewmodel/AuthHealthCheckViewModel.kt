package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthHealthCheckViewModel @Inject constructor(
    authRepository: AuthRepository
): ViewModel() {

    private val _isLoggedIn = MutableStateFlow (false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
}