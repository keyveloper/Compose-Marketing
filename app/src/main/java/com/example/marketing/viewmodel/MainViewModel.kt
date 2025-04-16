package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.MainScreenStatus
import com.example.marketing.enums.UserStatus
import com.example.marketing.repository.AdvertisementRepository
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
    private val influencerRepository: InfluencerRepository,
): ViewModel() {
    private val _mainStatus = MutableStateFlow(MainScreenStatus.GOLDEN)
    val mainStatus: StateFlow<MainScreenStatus> = _mainStatus.asStateFlow()

    private val _userStatus = MutableStateFlow(UserStatus.INFLUENCER)
    val userStatus: StateFlow<UserStatus> = _userStatus.asStateFlow()

    fun changeStatus(status: MainScreenStatus) {
        _mainStatus.value = status
    }
}