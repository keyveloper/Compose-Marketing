package com.example.marketing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.SignUpAdmin
import com.example.marketing.exception.BusinessException
import com.example.marketing.repository.AdminRepository
import com.example.marketing.state.SignUpAdminState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository
): ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpAdminState>(SignUpAdminState.Idle)

    val signUpState: StateFlow<SignUpAdminState> = _signUpState.asStateFlow()

    fun signUp(requestModel: SignUpAdmin) {
        viewModelScope.launch {
            _signUpState.value = SignUpAdminState.Loading
            try {

            } catch(e: BusinessException) {
                _signUpState.value = SignUpAdminState.Error(e.message)
            } catch (e: Exception) {
                _signUpState.value = SignUpAdminState.Error(e.message ?: "unexpected Exception")
                Log.e("AdminViewModel", "${e.message}")
            }
        }
    }
}