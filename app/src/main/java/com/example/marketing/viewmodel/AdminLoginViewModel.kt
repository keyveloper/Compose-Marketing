package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.LoginAdmin
import com.example.marketing.repository.AdminRepository
import com.example.marketing.state.LoginAdminState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    private val adminRepository: AdminRepository
): ViewModel() {
    private val _loginState = MutableStateFlow<LoginAdminState>(LoginAdminState.Idle)
    val loginState: StateFlow<LoginAdminState> = _loginState.asStateFlow()

    private val _loginId = MutableStateFlow("")
    val loginId: StateFlow<String> = _loginId.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateLoginId(loginId: String) {
        _loginId.value = loginId
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun login(requestModel: LoginAdmin) {
        viewModelScope.launch {
            
        }
    }
}