package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.MainScreenStatus
import com.example.marketing.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
): ViewModel() {
    private val _mainStatus = MutableStateFlow(MainScreenStatus.HOME)
    val mainStatus: StateFlow<MainScreenStatus> = _mainStatus.asStateFlow()

    fun changeStatus(status: MainScreenStatus) {
        _mainStatus.value = status
    }
}