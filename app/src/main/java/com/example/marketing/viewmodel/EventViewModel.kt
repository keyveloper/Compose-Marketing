package com.example.marketing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.marketing.domain.Advertisement
import com.example.marketing.enums.EventStatus
import com.example.marketing.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository
): ViewModel() {
    private val _eventStatus = MutableStateFlow ( EventStatus.FRESH )
    val eventStatus: StateFlow<EventStatus> = _eventStatus.asStateFlow()

    private val _items = MutableStateFlow<List<Advertisement>>( listOf() )
    val items: StateFlow<List<Advertisement>> = _items.asStateFlow()

    fun changeEventStatus(status: EventStatus) {
        _eventStatus.value = status
    }

    suspend fun fetchFresh() {

        val advertisements = withContext(Dispatchers.IO) {
            advertisementRepository.fetchFresh()
        }

        _items.value = advertisements
        Log.i("EventViewModel", "${_items.value}")
    }
}