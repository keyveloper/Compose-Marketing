package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdvertiserProfileResult
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.exception.AdvertiserProfileFetchVariableNotSetException
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
class AdvertiserProfileViewModel @Inject constructor(
    private val advertiserRepository: AdvertiserRepository
): ViewModel() {
    private val _advertiserId = MutableStateFlow(-1L)
    val advertiserId = _advertiserId.asStateFlow()

    private val _apiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val apiCallStatus = _apiCallStatus.asStateFlow()

    private val _profile = MutableStateFlow<AdvertiserProfileResult?>(null)
    val profile: StateFlow<AdvertiserProfileResult?> = _profile.asStateFlow()

    fun updateAdvertiserId(id: Long) {
        _advertiserId.value = id
    }

    private fun updateProfile(profile: AdvertiserProfileResult) {
        _profile.value = profile
    }

    private fun updateApiCallStatue(status: ApiCallStatus) {
        _apiCallStatus.value = status
    }

    fun fetchProfile(advertiserId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            updateApiCallStatue(ApiCallStatus.LOADING)
            if (_advertiserId.value == -1L) {
                updateApiCallStatue(ApiCallStatus.FAILED)
                throw AdvertiserProfileFetchVariableNotSetException(
                    advertiserId = _advertiserId.value,
                    viewmodelName = "AdvertiserProfileViewModel"
                )
            }

            val result = advertiserRepository.fetchProfile(advertiserId)

            withContext(Dispatchers.Main) {
                updateProfile(result)
                updateApiCallStatue(ApiCallStatus.SUCCESS)
            }
        }
    }
}