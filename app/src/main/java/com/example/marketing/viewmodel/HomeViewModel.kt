package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.HomeScreenStatus
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    // ------------✍️ input value -------------
    // ----------- 🚀 from server value -----------
    // ----------- 🎮 update function-------------
    // ----------- 🛜 API -----------------------
    // -------------🔍 inspection -----------


    // ------------🔃 status ------------
    private val _screenState = MutableStateFlow(HomeScreenStatus.Event)
    val screenState: StateFlow<HomeScreenStatus> = _screenState.asStateFlow()
    
    fun changeState(state: HomeScreenStatus) {
        _screenState.value = state
    }
}