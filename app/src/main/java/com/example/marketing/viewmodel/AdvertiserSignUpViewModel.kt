package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.SignUpAdvertiser
import com.example.marketing.enums.AdvertiserSignUpPart
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

    // ------------✍️ input value -------------
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

    private val _email = MutableStateFlow<String?> (null)
    val email = _email.asStateFlow()
    
    private val _contact = MutableStateFlow<String?> (null)
    val contact = _contact.asStateFlow()

    // ------------🔃 status ------------

    private val _SignUpApiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val SignUpApiCallStatus = _SignUpApiCallStatus.asStateFlow()

    private val _part = MutableStateFlow(AdvertiserSignUpPart.CREDENTIAL)
    val part: StateFlow<AdvertiserSignUpPart> = _part.asStateFlow()


    // ----------- 🎮 update function-------------
    fun updatePart(status: AdvertiserSignUpPart) {
        _part.value = status
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

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateContact(contact: String) {
        _contact.value = contact
    }

    private fun updateSignUpApiCallStatus(status: ApiCallStatus) {
        _SignUpApiCallStatus.value = status
    }

    // -------------🔍 inspection -----------

    fun checkApiAllowed(): Boolean {
        return _loginId.value.isNotEmpty() && _password.value.isNotEmpty() &&
                _companyName.value.isNotEmpty() && _email.value != null &&
                _contact.value != null
    }


    fun signUp() {

        if (checkApiAllowed()) {
            val signUpAdvertiser = SignUpAdvertiser(
                loginId = _loginId.value,
                password = _password.value,
                homepageUrl = _homepageUrl.value,
                companyName = _companyName.value,
                advertiserType = _advertiserType.value,
                email = _email.value!!,
                contact = _contact.value!!
            )

            viewModelScope.launch(Dispatchers.IO) {
                updateSignUpApiCallStatus(ApiCallStatus.LOADING)
                val createdId = advertiserRepository.signUp(signUpAdvertiser)

                if (createdId != null) {
                    withContext(Dispatchers.Main) {
                        updateSignUpApiCallStatus(ApiCallStatus.SUCCESS)
                    }
                }
            }
        }
    }

}