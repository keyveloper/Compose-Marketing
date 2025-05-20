package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.domain.AdvertiserProfileInfo
import com.example.marketing.dto.functions.request.FollowAdvertiser
import com.example.marketing.enums.AdvertiserProfileAdMode
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.FollowStatus
import com.example.marketing.repository.AdvertiserRepository
import com.example.marketing.repository.FollowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertiserProfileViewModel @Inject constructor(
    private val advertiserProfileRepository: AdvertiserRepository,
    private val followRepository: FollowRepository
): ViewModel() {
    // ------------ ⛏️ init -------------
    private val _profileInfo = MutableStateFlow<AdvertiserProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    // ----------- 🔃 status ------------
    private val _apiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val apiCallStatus = _apiCallStatus.asStateFlow()

    private val _adMode = MutableStateFlow(AdvertiserProfileAdMode.LIVE)
    val adMode = _adMode.asStateFlow()

    private val _followStatus = MutableStateFlow(FollowStatus.UNFOLLOW)
    val followStatus = _followStatus.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _livePackages = MutableStateFlow<List<AdvertisementPackage>>( listOf() )
    val livePackages = _livePackages.asStateFlow()

    private val _expiredPackages = MutableStateFlow<List<AdvertisementPackage>>( listOf() )
    val expiredPackages = _expiredPackages.asStateFlow()


    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateProfileInfo(profileInfo: AdvertiserProfileInfo) = run {
        _profileInfo.value = profileInfo
    }

    private fun updateApiCallStatue(status: ApiCallStatus) = run {
        _apiCallStatus.value = status
    }

    private fun updateLivePackages(packages: List<AdvertisementPackage>) = run {
        _livePackages.value = packages
    }

    private fun updateExpiredPackages(packages: List<AdvertisementPackage>) = run {
        _expiredPackages.value = packages
    }

    fun updateAdMode(mode: AdvertiserProfileAdMode) = run {
        _adMode.value = mode
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    suspend fun fetchLiveAdvertisementPackages() {
        if (_profileInfo.value != null) {
            withContext(Dispatchers.IO) {
                val packages =advertiserProfileRepository.fetchLiveAdvertisements(
                    _profileInfo.value!!.advertiserId)

                withContext(Dispatchers.Main) {
                    updateLivePackages(packages)
                }
            }
        }
    }

    suspend fun fetchExpiredAdvertisementPackages() {
        if (_profileInfo.value != null) {
            withContext(Dispatchers.IO) {
                val packages =advertiserProfileRepository.fetchExpiredAdvertisements(
                    _profileInfo.value!!.advertiserId)

                withContext(Dispatchers.Main) {
                    updateExpiredPackages(packages)
                }
            }
        }
    }

    fun follow() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val result = followRepository.follow(
                FollowAdvertiser.of(_profileInfo.value!!.advertiserId)
            )

            if (result != null) {
                withContext(Dispatchers.Main) {
                    _followStatus.value = result.followStatus
                }
            }
        }
    }


}