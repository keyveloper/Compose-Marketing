package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.InfluencerJoinedProfileInfo
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerProfileViewModel @Inject constructor (
    private val influencerRepository: InfluencerRepository
): ViewModel() {

    // ------------✍️ input value -------------
    private val _influencerId = MutableStateFlow<Long?> (null)
    val influencerId = _influencerId.asStateFlow()


    // ------------🔃 status ------------


    // ----------- 🚀 from server value -----------
    private val _profileInfo = MutableStateFlow<InfluencerJoinedProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    private val _fetchedProfileImageByte = MutableStateFlow<ByteArray?> (null)
    val fetchedProfileImageByte = _fetchedProfileImageByte.value


    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateInfluencerId(id: Long) = run {
        _influencerId.value = id
    }

    fun updateProfileInfo(info: InfluencerJoinedProfileInfo) = run {
        _profileInfo.value = info
    }

    fun updateProfileImageByte(bytes: ByteArray) = run {
        _fetchedProfileImageByte.value = bytes
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    suspend fun fetchProfileInfo() =
        withContext(Dispatchers.IO) {
            if (_influencerId.value != null) {
                val domain = influencerRepository.fetchProfileInfo(_influencerId.value!!)
                    ?: return@withContext

                val unifiedCode = domain.unifiedImageCode

                if (unifiedCode != null) {
                    val imageBytes = async(Dispatchers.IO) {
                        influencerRepository.fetchProfileImageByte(unifiedCode)
                    }.await()

                    withContext(Dispatchers.Main) {
                        updateProfileInfo(domain)
                        updateProfileImageByte(imageBytes)

                    }
                } else {
                    withContext(Dispatchers.Main) {
                        updateProfileInfo(domain)
                    }
                }
            }
        }

}