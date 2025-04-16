package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.LoginAdvertiser
import com.example.marketing.repository.AdvertiseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertiserLoginViewModel @Inject constructor(
    private val advertiserRepository: AdvertiseRepository
): ViewModel() {

    private val _loginId = MutableStateFlow ("")
    val loginId = _loginId.asStateFlow()

    private val _password = MutableStateFlow ("")
    val password = _password.asStateFlow()

    fun updateLoginId(id: String) {
        _loginId.value = id
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            advertiserRepository.login(
                LoginAdvertiser.of(_loginId.value, _password.value)
            )
        }
    }
}