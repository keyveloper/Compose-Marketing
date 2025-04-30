package com.example.marketing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.enums.UserType
import com.example.marketing.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthHealthCheckViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    val tokenFlow: StateFlow<String?> = authRepository.observeToken()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _loginStatus = MutableStateFlow (false)
    val loginStatus: StateFlow<Boolean> = _loginStatus.asStateFlow()


    private val _userType: MutableStateFlow<UserType?> = MutableStateFlow (null)
    val userType = _userType.asStateFlow()

    private val _userId: MutableStateFlow<Long?> = MutableStateFlow(null)
    val userId = _userId.asStateFlow()


    private fun updateLoginStatus(status: Boolean) {
        _loginStatus.value = status
    }

    private fun updateUserType(type: UserType) {
        _userType.value = type
    }

    private fun updateUserId(id: Long) {
        _userId.value = id
    }

    fun validateToken() {
        val token = tokenFlow.value
        if (token == null) {
            Log.i("authHealthCheckVm", "invalid token...")
            updateLoginStatus(false)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.validateToken()
            withContext(Dispatchers.Main) {
                if (result == null) {
                    Log.i("authHealthCheckVm", "validate failed → null result")
                    updateLoginStatus(false)
                } else {
                    updateLoginStatus(true)
                    updateUserType(result.userType)
                    result.userId?.let { updateUserId(it) }
                }

                Log.i(
                    "authHealthCheckVm",
                    "loginStatus: " +
                            "${_loginStatus.value} userType:" +
                            " ${_userType.value} userId: ${_userId.value}"
                )
            }
        }
    }
}