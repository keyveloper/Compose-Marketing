package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.domain.AdvertiserProfileInfo
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.repository.AdvertiserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdvertiserProfileViewModel @Inject constructor(
    private val advertiserRepository: AdvertiserRepository
): ViewModel() {
    // ------------ ⛏️ init -------------
    private val _profileInfo = MutableStateFlow<AdvertiserProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    // ----------- 🔃 status ------------
    private val _apiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val apiCallStatus = _apiCallStatus.asStateFlow()


    // ----------- 🚀 from server value -----------



    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateProfileInfo(profileInfo: AdvertiserProfileInfo) = run {
        _profileInfo.value = profileInfo
    }

    private fun updateApiCallStatue(status: ApiCallStatus) {
        _apiCallStatus.value = status
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------

}