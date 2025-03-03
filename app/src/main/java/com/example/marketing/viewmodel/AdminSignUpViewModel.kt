package com.example.marketing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.SignUpAdmin
import com.example.marketing.exception.BusinessException
import com.example.marketing.repository.AdminRepository
import com.example.marketing.state.SignUpAdminState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminSignUpViewModel @Inject constructor(
    private val adminRepository: AdminRepository
): ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpAdminState>(SignUpAdminState.Idle)
    val signUpState: StateFlow<SignUpAdminState> = _signUpState.asStateFlow()

    private val _loginId = MutableStateFlow ("")
    val loginId: StateFlow<String> = _loginId.asStateFlow()

    private val _password = MutableStateFlow ("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _createdId = MutableStateFlow (-1L)
    val createdId: StateFlow<Long> = _createdId.asStateFlow()

    fun updatedLoginId(loginId: String) {
        _loginId.value = loginId
    }

    fun updatedPassword(password: String) {
        _password.value = password
    }

    fun signUp(requestModel: SignUpAdmin) {
        viewModelScope.launch {
            _signUpState.value = SignUpAdminState.Loading
            try {
                val createdId = adminRepository.signUp(requestModel)

                viewModelScope.launch(Dispatchers.Main) {
                    _createdId.value = createdId
                    _signUpState.value = SignUpAdminState.Success(createdId)
                }

            } catch(e: BusinessException) {
                _signUpState.value = SignUpAdminState.Error(e.message)
            } catch (e: Exception) {
                _signUpState.value = SignUpAdminState.Error(e.message ?: "unexpected Exception")
                Log.e("AdminViewModel", "${e.message}")
            }
        }
    }
}