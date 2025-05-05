package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.board.request.GetAllDeliveriesTimelineByCategory
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimelineDirection
import com.example.marketing.repository.AdvertisementDeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val advertisementDeliveryRepository: AdvertisementDeliveryRepository
): ViewModel() {

}