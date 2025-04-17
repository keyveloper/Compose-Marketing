package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.SignUpAdvertiser
import com.example.marketing.enums.AdvertiserSignUpStatus
import com.example.marketing.enums.AdvertiserType
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.repository.AdvertiserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertiserSignUpViewModel @Inject constructor(
    private val advertiserRepository: AdvertiserRepository
): ViewModel() {
    private val _apiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val apiCallStatus = _apiCallStatus.asStateFlow()

    private val _liveStatus = MutableStateFlow(AdvertiserSignUpStatus.CREDENTIAL)
    val liveStatus: StateFlow<AdvertiserSignUpStatus> = _liveStatus.asStateFlow()

    private val _loginId = MutableStateFlow("")
    val loginId: StateFlow<String> = _loginId.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _advertiserType = MutableStateFlow(AdvertiserType.NORMAL)
    val advertiserType = _advertiserType.asStateFlow()

    private val _companyName = MutableStateFlow("")
    val companyName = _companyName.asStateFlow()

    private val _homepageUrl = MutableStateFlow<String?> (null)
    val homepageUrl = _homepageUrl.asStateFlow()

    fun updateLiveStatus(status: AdvertiserSignUpStatus) {
        _liveStatus.value = status
    }

    fun updateLoginId(id: String) {
        _loginId.value = id
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateAdvertiserType(type: AdvertiserType) {
        _advertiserType.value = type
    }

    fun updateHomepageUrl(url: String) {
        _homepageUrl.value = url
    }

    fun updateCompanyName(name: String) {
        _companyName.value = name
    }

    private fun updateApiCallStatus(status: ApiCallStatus) {
        _apiCallStatus.value = status
    }

    fun signUp() {
        val signUpAdvertiser = SignUpAdvertiser(
            loginId = _loginId.value,
            password = _password.value,
            homepageUrl = null,
            companyName = _companyName.value,
            advertiserType = _advertiserType.value
        )

        viewModelScope.launch(Dispatchers.IO) {
            updateApiCallStatus(ApiCallStatus.LOADING)
            advertiserRepository.signUp(signUpAdvertiser)

            withContext(Dispatchers.Main) {
                updateApiCallStatus(ApiCallStatus.SUCCESS)
            }
        }
    }

}