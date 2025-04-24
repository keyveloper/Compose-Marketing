package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdvertisementDelivery
import com.example.marketing.dto.board.request.GetAllDeliveriesTimelineByCategory
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimeLineDirection
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
    private val _liveAdvertisements: MutableStateFlow<List<AdvertisementDelivery>> =
        MutableStateFlow(listOf())
    val liveAdvertisements: StateFlow<List<AdvertisementDelivery>> =
        _liveAdvertisements.asStateFlow()

    private val _apiCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val apiCAllStatus = _apiCallStatus.asStateFlow()

    private val _currentCategoryStatus = MutableStateFlow(DeliveryCategory.LIFE)
    val currentCategoryStatus = _currentCategoryStatus.asStateFlow()

    fun updateLiveAdvertisements(newList: List<AdvertisementDelivery>) {
        _liveAdvertisements.value = newList
    }

    fun appendLiveAdvertisements(newItems: List<AdvertisementDelivery>) {
        _liveAdvertisements.value = _liveAdvertisements.value + newItems
    }

    fun updateApiCallStatus(newStatus: ApiCallStatus) {
        _apiCallStatus.value = newStatus
    }

    fun updateCurrentCategoryStatus(category: DeliveryCategory) {
        _currentCategoryStatus.value = category
    }

    fun clearLiveAdvertisement() {
        _liveAdvertisements.value = listOf()
    }


    fun fetchTimelineInitByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val advertisements = advertisementDeliveryRepository
                .fetchAllTimelineByCategoryAndDirection(
                requestModel = GetAllDeliveriesTimelineByCategory.of(
                    deliveryCategory = currentCategoryStatus.value,
                    pivotTime = System.currentTimeMillis(),
                    timeLineDirection = TimeLineDirection.INIT
                )
            )

            withContext(Dispatchers.Main) {
                clearLiveAdvertisement()
                appendLiveAdvertisements(advertisements)
            }
        }
    }

    fun fetchTimelineNextByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val advertisements = advertisementDeliveryRepository
                .fetchAllTimelineByCategoryAndDirection(
                requestModel = GetAllDeliveriesTimelineByCategory.of(
                    deliveryCategory = currentCategoryStatus.value,
                    pivotTime = liveAdvertisements.value.minOfOrNull {
                        it.advertisementGeneral.createdAt
                    } ?: System.currentTimeMillis(),
                    timeLineDirection = TimeLineDirection.NEXT
                )
            )
            withContext(Dispatchers.Main) {
                appendLiveAdvertisements(advertisements)
            }
        }
    }

    fun fetchTimelinePrevByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val advertisements = advertisementDeliveryRepository
                .fetchAllTimelineByCategoryAndDirection(
                requestModel = GetAllDeliveriesTimelineByCategory.of(
                    deliveryCategory = currentCategoryStatus.value,
                    pivotTime = liveAdvertisements.value.minOfOrNull {
                        it.advertisementGeneral.createdAt
                    } ?: System.currentTimeMillis(),
                    timeLineDirection = TimeLineDirection.PREV
                )
            )

            withContext(Dispatchers.Main) {
                appendLiveAdvertisements(advertisements)
            }
        }
    }
}