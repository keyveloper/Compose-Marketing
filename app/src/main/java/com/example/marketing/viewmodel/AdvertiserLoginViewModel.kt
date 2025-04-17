package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.LoginAdvertiser
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.repository.AdvertiserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertiserLoginViewModel @Inject constructor(
    private val advertiserRepository: AdvertiserRepository
): ViewModel() {

    private val _loginId = MutableStateFlow ("")
    val loginId = _loginId.asStateFlow()

    private val _password = MutableStateFlow ("")
    val password = _password.asStateFlow()

    private val _advertiserId = MutableStateFlow (-1L)
    val advertiserId = _advertiserId.asStateFlow()

    private val _apiCallStatus = MutableStateFlow (ApiCallStatus.IDLE)
    val apiCallStatus = _apiCallStatus.asStateFlow()

    fun updateLoginId(id: String) {
        _loginId.value = id
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    private fun updateAdvertiserId(id: Long) {
        _advertiserId.value = id
    }

    private fun updateApiCallStatus(status: ApiCallStatus) {
        _apiCallStatus.value = status
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            updateApiCallStatus(ApiCallStatus.LOADING)
            val result = advertiserRepository.login(
                LoginAdvertiser.of(_loginId.value, _password.value)
            )

            withContext(Dispatchers.Main) {
                updateAdvertiserId(result)
                updateApiCallStatus(ApiCallStatus.SUCCESS)
            }
        } // how to catch the error?
    }
}