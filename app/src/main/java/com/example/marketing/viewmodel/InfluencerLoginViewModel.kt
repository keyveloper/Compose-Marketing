package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.repository.AuthRepository
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerLoginViewModel @Inject constructor(
    private val influencerRepository: InfluencerRepository,
    private val authRRepository: AuthRepository
): ViewModel(){

    // ------------✍️ input value -------------
    private val _loginId = MutableStateFlow<String?> (null)
    val loginId = _loginId.asStateFlow()

    private val _password = MutableStateFlow<String?> (null)
    val password = _password.asStateFlow()

    // ------------🔃 status ------------
    val _loginApiCallStatus = MutableStateFlow<ApiCallStatus> (ApiCallStatus.IDLE)
    val loginApiCallStatus = _loginApiCallStatus.asStateFlow()

    // ----------- 🚀 from server value -----------
    val _influencerId = MutableStateFlow<Long?> (null)
    val influencerId = _influencerId.asStateFlow()

    // ----------- 🎮 update function-------------
    fun updateLoginId(loginId: String) = run { _loginId.value = loginId }

    fun updatePassword(password: String) = run { _password.value = password }

    fun updateInfluencerId(id: Long) = run { _influencerId.value = id }

    fun updateLoginCallStatus(status: ApiCallStatus) = run { _loginApiCallStatus.value = status }

    fun checkApiAllowed(): Boolean {
        return if (_loginId.value != null && _password.value != null) {
            true
        } else false
    }

    // ----------- 🛜 API -----------------------
    suspend fun login() {
        val apiAllowed = checkApiAllowed()

        if (apiAllowed) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = influencerRepository.login(_loginId.value!!, _password.value!!)

                if (result != null) {
                    withContext(Dispatchers.Main) {
                        updateInfluencerId(result.influencerId)
                        authRRepository.saveToken(result.jwt)

                        updateLoginCallStatus(ApiCallStatus.SUCCESS)
                    }
                }
            }
        } else {
            // Log?
        }
    }
}