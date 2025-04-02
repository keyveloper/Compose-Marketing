package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InfluencerSignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _credentialPartLive = MutableStateFlow (true)
    val credentialPartLive = _credentialPartLive.asStateFlow()

    private val _loginId = MutableStateFlow ("")
    val loginId = _loginId.asStateFlow()

    private val _loginIdFilled = MutableStateFlow (false)
    val loginIdFilled = _loginIdFilled.asStateFlow()

    private val _password = MutableStateFlow ("")
    val password = _password.asStateFlow()

    private val _passwordValidation = MutableStateFlow ("")
    val passwordValidation = _passwordValidation.asStateFlow()

    private val _passwordValidationStatus = MutableStateFlow (false)
    val passwordValidationStatus = _passwordValidationStatus.asStateFlow()

    private val _credentialStatus = MutableStateFlow (false)
    val credentialStatus = _credentialStatus.asStateFlow()

    // details part
    private val _detailPartLive = MutableStateFlow (false)
    val detailPartStatus = _detailPartLive.asStateFlow()

    private val _datePickerLive = MutableStateFlow (false)
    val datePickerLive = _datePickerLive.asStateFlow()

    private val _date = MutableStateFlow ("")
    val date = _date.asStateFlow()


    // channel part
    private val _channelPartLive = MutableStateFlow (false)
    val channelPartStatus = _channelPartLive.asStateFlow()


    fun setLoginId(loginId: String) {
        _loginId.value = loginId
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun updateLoginFilled(isFilled: Boolean) {
        _loginIdFilled.value = isFilled
    }

    fun setPasswordValidation(passwordValidation: String) {
        _passwordValidation.value = passwordValidation
    }

    fun updateCredentialStatus(isCredential: Boolean) {
        _credentialStatus.value = isCredential
    }

    fun updatePasswordValidationStatus(isValid: Boolean) {
        _passwordValidationStatus.value = isValid
    }

    // part change
    fun updateCredentialPartLive(isLive: Boolean) {
        _credentialPartLive.value = isLive
    }

    fun updateDetailPartLive(isLive: Boolean) {
        _detailPartLive.value = isLive
    }

    fun updateChannelPertLive(isLive: Boolean) {
        _channelPartLive.value = isLive
    }

    fun updateDatePickerLive(isLive: Boolean) {
        _datePickerLive.value = isLive
    }

    fun updateDate(date: String) {
        _date.value = date
    }


    fun nextToCredentialPart() {
        updateCredentialPartLive(true)
        updateChannelPertLive(false)
        updateDetailPartLive(false)
    }

    fun nextToDetailPart() {
        updateCredentialStatus(false)
        updateDetailPartLive(true)
        updateChannelPertLive(false)
        updateDatePickerLive(true)
    }

    fun nextToChannelPart() {
        updateCredentialPartLive(false)
        updateDetailPartLive(false)
        updateChannelPertLive(true)
    }


    // api
    fun validCredential() {

    }
}