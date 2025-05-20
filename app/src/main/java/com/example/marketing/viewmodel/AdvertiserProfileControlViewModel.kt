package com.example.marketing.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdvertiserProfileInfo
import com.example.marketing.domain.InfluencerProfileImage
import com.example.marketing.enums.ProfileMode
import com.example.marketing.enums.UserType
import com.example.marketing.repository.AdvertiserRepository
import com.example.marketing.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertiserProfileControlViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val advertiserRepository: AdvertiserRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    // ------------⛏️️ init value -------------
    private val refresh = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    private val _userTypeStr =
        savedStateHandle.getStateFlow("userType", UserType.VISITOR.name)
    val userType: StateFlow<UserType> = _userTypeStr
        .map { enumValueOf<UserType>(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserType.VISITOR)

    private val _advertiserId =
        savedStateHandle.getStateFlow("advertiserId", null)
    val advertiserId: StateFlow<Long?> = _advertiserId

    // ------------🔃 status ------------
    private val _profileMode = MutableStateFlow (ProfileMode.INIT)
    val profileMode = _profileMode.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _profileInfo = MutableStateFlow<AdvertiserProfileInfo?> (null)
    val profileInfo = _profileInfo.asStateFlow()

    private val _imageMetaData = MutableStateFlow<InfluencerProfileImage?>(null)


    // ----------- 🎮 update function-------------
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
        withContext(Dispatchers.IO) {
            val profile = advertiserRepository.fetchProfileInfo(advertiserId.value ?: -1L)

            if (profile != null) {
                withContext(Dispatchers.Main) {
                    updateProfileInfo(profile)
                    _profileMode.value = ProfileMode.READ_ONLY
                }
            }
        }
    }
}