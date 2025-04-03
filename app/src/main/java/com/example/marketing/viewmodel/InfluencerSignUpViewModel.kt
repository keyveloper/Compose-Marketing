package com.example.marketing.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.marketing.repository.AuthRepository
import com.example.marketing.ui.item.ChannelFloatingObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InfluencerSignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    // part
    // 1. credential
    private val _credentialPartLive = MutableStateFlow (false)
    val credentialPartLive = _credentialPartLive.asStateFlow()

    // 2. detail
    private val _detailPartLive = MutableStateFlow (false)
    val detailPartStatus = _detailPartLive.asStateFlow()

    // 3. channel
    private val _channelPartLive = MutableStateFlow (true)
    val channelPartStatus = _channelPartLive.asStateFlow()

    // 1+. credential model
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

    private val _formattedDate = MutableStateFlow ("")
    val formattedDate = _formattedDate.asStateFlow()

    // 3+. channel
    private val _channels = MutableStateFlow<List<ChannelFloatingObject>> (
        listOf()
    )
    val channel = _channels.asStateFlow()

    private val _primaryId = MutableStateFlow (0)
    val primaryId = _primaryId.asStateFlow()

    val channels: StateFlow<List<ChannelFloatingObject>> = _channels.asStateFlow()

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


    fun setFormattedDate(date: String) {
        _formattedDate.value = date

    }

    fun addChannel(
        channel: ChannelFloatingObject
    ) {
        _channels.value += channel
    }

    fun updateChannelCardPosition(id: Int, offset: Offset) {
        _channels.update { list ->
            list.map { item ->
                if (item.id == id) item.copy(currentPosition = offset)
                else item
            }
        }
    }

    fun resetChannelCardToDefaultPosition(id: Int) {
        _channels.update { list ->
            list.map { item ->
                if (item.id == id) item.copy(currentPosition = item.defaultPosition)
                else item
            }
        }
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