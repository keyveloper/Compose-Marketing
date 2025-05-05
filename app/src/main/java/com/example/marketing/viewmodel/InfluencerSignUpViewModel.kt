package com.example.marketing.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.user.request.SignUpInfluencer
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.InfluencerSignUpPart
import com.example.marketing.repository.InfluencerRepository
import com.example.marketing.ui.item.ChannelFloatingObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerSignUpViewModel @Inject constructor(
    private val influencerRepository: InfluencerRepository
): ViewModel() {

    // ------------✍️ input value -------------
    private val _loginId = MutableStateFlow ("")
    val loginId = _loginId.asStateFlow()

    private val _password = MutableStateFlow ("")
    val password = _password.asStateFlow()

    private val _passwordValidation = MutableStateFlow ("")
    val passwordValidation = _passwordValidation.asStateFlow()

    private val _formattedBirthday = MutableStateFlow ("")
    val formattedBirthday = _formattedBirthday.asStateFlow()

    private val _channelCards = MutableStateFlow<List<ChannelFloatingObject>> (
        listOf()
    )
    val channelCards = _channelCards.asStateFlow()

    // ------------🔃 status ------------
    private val _part = MutableStateFlow(InfluencerSignUpPart.CREDENTIAL)
    val part = _part.asStateFlow()

    private val _signUpApiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val signUpApiCallStatus = _signUpApiCallStatus.asStateFlow()

    // ----------- 🎮 update function-------------
    fun updateLoginId(loginId: String)    = run { _loginId.value  = loginId }
    fun updatePassword(password: String)  = run { _password.value  = password }
    fun updatePasswordValidation(validation: String)    = run { _passwordValidation.value = validation }
    fun updateFormattedDate(date: String)  = run { _formattedBirthday.value  = date }
    fun updatePasswordValidationStatus(isValid: Boolean)
            = run { _passwordValidationStatus.value = isValid }

    fun updatePart(part: InfluencerSignUpPart) = run { _part.value = part }

    fun updateSignUpApiCallStatus(status: ApiCallStatus) = run {
        _signUpApiCallStatus.value = status
    }

    fun addChannel(
        channel: ChannelFloatingObject
    ) {
        _channelCards.value += channel
    }

    fun deleteChannel(channel: ChannelFloatingObject) {
        _channelCards.value -= channel
    }

    // -------------🔍 inspection -----------
    fun mathPasswords(): Boolean {
        return _password.value == _passwordValidation.value
    }

    private fun checkApiAllowed(): Boolean {
        return _loginId.value.isNotEmpty() && _password.value.isNotEmpty() &&
                _formattedBirthday.value.isNotEmpty()
    }


    // ----------- 🚀 from server value ----------


    // ----------- 🛜 API ------------------------
    suspend fun signUp() {
        val isAllowed = checkApiAllowed()

        if (isAllowed) {
            viewModelScope.launch(Dispatchers.IO) {
                val createdId =  influencerRepository.signUp(
                    SignUpInfluencer.of(
                        _loginId.value,
                        _password.value,
                        _formattedBirthday.value,
                        _channelCards.value
                    )
                )

                if (createdId != null) {
                    withContext(Dispatchers.Main) {
                        updateSignUpApiCallStatus(ApiCallStatus.SUCCESS)
                    }
                }
            }
        }
    }

    private val _primaryId = MutableStateFlow (0)
    val primaryId = _primaryId.asStateFlow()

    private val _passwordValidationStatus = MutableStateFlow (false)
    val passwordValidationStatus = _passwordValidationStatus.asStateFlow()


    fun updateChannelCardPosition(id: Int, offset: Offset) {
        _channelCards.update { list ->
            list.map { item ->
                if (item.id == id) item.copy(currentPosition = offset)
                else item
            }
        }
    }

    fun resetChannelCardToDefaultPosition(id: Int) {
        _channelCards.update { list ->
            list.map { item ->
                if (item.id == id) item.copy(currentPosition = item.defaultPosition)
                else item
            }
        }
    }
}