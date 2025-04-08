package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.AdvertiserSignUpStatus
import com.example.marketing.enums.UserType
import com.example.marketing.repository.AdvertiseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdvertiserSignUpViewModel @Inject constructor(
    private val advertiserRepository: AdvertiseRepository
): ViewModel() {

    private val _liveStatus = MutableStateFlow(AdvertiserSignUpStatus.MAP)
    val liveStatus: StateFlow<AdvertiserSignUpStatus> = _liveStatus.asStateFlow()

    private val _loginId = MutableStateFlow("")
    val loginId: StateFlow<String> = _loginId.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _userType = MutableStateFlow(UserType.ADVERTISER_COMMON)
    val userType: StateFlow<UserType> = _userType.asStateFlow()

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

    fun updateUserType(userType: UserType) {
        _userType.value = userType
    }

    fun updateHomepageUrl(url: String) {
        _homepageUrl.value = url
    }

    fun updateCompanyName(name: String) {
        _companyName.value = name
    }

}