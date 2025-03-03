package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.SignUpAdmin
import com.example.marketing.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository
): ViewModel() {
    fun signUp(requestModel: SignUpAdmin) {
        viewModelScope.launch {
            adminRepository.signUp(requestModel)
        }
    }
}