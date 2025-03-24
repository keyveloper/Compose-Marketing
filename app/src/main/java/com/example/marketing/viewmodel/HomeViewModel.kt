package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.HomeContentStatus
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
): ViewModel() {
    private val _contentState = MutableStateFlow(HomeContentStatus.FRESH)
    val contentState: StateFlow<HomeContentStatus> = _contentState.asStateFlow()

    private val _advertisementThumbnails =
        MutableStateFlow<List<AdvertisementThumbnailItem>>(listOf())
    val advertisementThumbnails: StateFlow<List<AdvertisementThumbnailItem>> =
        _advertisementThumbnails.asStateFlow()

    fun fetchFresh() {

    }
}