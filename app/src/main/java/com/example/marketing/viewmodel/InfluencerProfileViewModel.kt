package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.domain.InfluencerJoinedProfileInfo
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerProfileViewModel @Inject constructor (
    private val influencerRepository: InfluencerRepository
): ViewModel() {


    // ------------🔃 status ------------


    // ----------- 🚀 from server value -----------
    private val _profileInfo = MutableStateFlow<InfluencerJoinedProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    private val _fetchedProfileImageByte = MutableStateFlow<ByteArray?> (null)
    val fetchedProfileImageByte = _fetchedProfileImageByte.asStateFlow()


    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun init(profileInfo: InfluencerJoinedProfileInfo) {
        _profileInfo.value = profileInfo
    }

    private fun updateProfileImageByte(bytes: ByteArray) = run {
        _fetchedProfileImageByte.value = bytes
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    suspend fun fetchImageBytes() {
        if(_profileInfo.value != null && _fetchedProfileImageByte.value == null) {
            val unifiedCode = _profileInfo.value!!.unifiedImageCode

            if (unifiedCode != null) {
                withContext(Dispatchers.IO) {
                    val imageBytes = influencerRepository.fetchProfileImageByte(unifiedCode)

                    withContext(Dispatchers.Main) {
                        updateProfileImageByte(imageBytes)
                    }
                }
            }
        }
    }
}