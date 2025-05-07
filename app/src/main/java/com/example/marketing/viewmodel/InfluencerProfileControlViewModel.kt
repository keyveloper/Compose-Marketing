package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.domain.InfluencerJoinedProfileInfo
import com.example.marketing.domain.InfluencerProfileImage
import com.example.marketing.enums.ProfileMode
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerProfileControlViewModel @Inject constructor(
    private val influencerRepository: InfluencerRepository
): ViewModel() {
    // ------------⛏️️ init value -------------
    private val _influencerId = MutableStateFlow<Long?> (null)
    val influencerId = _influencerId.asStateFlow()

    // ------------🔃 status ------------
    private val _profileMode = MutableStateFlow (ProfileMode.INIT)
    val profileMode = _profileMode.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _profileInfo = MutableStateFlow<InfluencerJoinedProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    private val _imageMetaData = MutableStateFlow<InfluencerProfileImage?>(null)


    // ----------- 🎮 update function-------------
    fun updateInfluencerId(id: Long) = run {
        _influencerId.value = id
    }

    fun updateProfileMode(mode: ProfileMode) = run {
        _profileMode.value = mode
    }

    private fun updateProfileInfo(info: InfluencerJoinedProfileInfo) = run {
        _profileInfo.value = info
    }


    // ----------- 🛜 API -----------------------
    suspend fun fetchProfileInfo()  {
        withContext(Dispatchers.IO) {
            if (_influencerId.value != null) {
                val domain = influencerRepository.fetchProfileInfo(_influencerId.value!!)
                    ?: return@withContext

                withContext(Dispatchers.Main) {
                    updateProfileInfo(domain)
                    _profileMode.value = ProfileMode.READ_ONLY
                }
            }
        }
    }


}