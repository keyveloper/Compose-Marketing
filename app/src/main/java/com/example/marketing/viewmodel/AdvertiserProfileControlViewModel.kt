package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.domain.AdvertiserProfileInfo
import com.example.marketing.domain.InfluencerProfileImage
import com.example.marketing.enums.ProfileMode
import com.example.marketing.repository.AdvertiserRepository
import com.example.marketing.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertiserProfileControlViewModel @Inject constructor(
    private val advertiserRepository: AdvertiserRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    // ------------⛏️️ init value -------------
    private val _advertiserId = MutableStateFlow<Long?> (null)
    val advertiserId = _advertiserId.asStateFlow()

    // ------------🔃 status ------------
    private val _profileMode = MutableStateFlow (ProfileMode.INIT)
    val profileMode = _profileMode.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _profileInfo = MutableStateFlow<AdvertiserProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    private val _imageMetaData = MutableStateFlow<InfluencerProfileImage?>(null)


    // ----------- 🎮 update function-------------
    fun updateAdvertiserId(id: Long) = run {
        _advertiserId.value = id
    }

    fun updateProfileMode(mode: ProfileMode) = run {
        _profileMode.value = mode
    }

    private fun updateProfileInfo(info: AdvertiserProfileInfo) = run {
        _profileInfo.value = info
    }

    suspend fun logOut() {
        authRepository.clearToken()
    }

    // ----------- 🛜 API -----------------------
    suspend fun fetchProfileInfo()  {
        if (_advertiserId.value != null) {
            withContext(Dispatchers.IO) {
                val profile = advertiserRepository.fetchProfileInfo(_advertiserId.value!!)

                if (profile != null) {
                    withContext(Dispatchers.Main) {
                        updateProfileInfo(profile)
                        _profileMode.value = ProfileMode.READ_ONLY
                    }
                }
            }
        }

    }
}