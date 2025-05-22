package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategory
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimelineDirection
import com.example.marketing.repository.AdvertisementDeliveryRepository
import com.example.marketing.repository.FavoriteRepository
import com.example.marketing.state.AdCategoryInitState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val advertisementDeliveryRepository: AdvertisementDeliveryRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    // ------------⛏️ init ---------------
    val refresh = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    // ------------✍️ input value -------------
    // ------------🔃 status ------------
    private val _selectedCategory = MutableStateFlow(DeliveryCategory.ALL)
    val selectedCategory: StateFlow<DeliveryCategory> = _selectedCategory.asStateFlow()

    private val _pivotTime = MutableStateFlow(0L)
    val pivotTime = _pivotTime.asStateFlow()

    private val _timelineDirection = MutableStateFlow(TimelineDirection.INIT)
    val timelineDirection = _timelineDirection.asStateFlow()

    // Exposed reactive state (auto-triggers on category change)
    @OptIn(ExperimentalCoroutinesApi::class)
    val initState: StateFlow<AdCategoryInitState> = _selectedCategory
        .flatMapLatest { category ->
            flow {
                emit(AdCategoryInitState.Loading)

                val result = runCatching {
                    val categoriesToSend: List<DeliveryCategory> =
                        if (category == DeliveryCategory.ALL) DeliveryCategory.actualCategories
                        else listOf(category)

                    advertisementDeliveryRepository.fetchAllTimelineByCategoryAndDirection(
                        GetDeliveryAdvertisementsTimelineByCategory.of(
                            pivotTime = _pivotTime.value,
                            timelineDirection = _timelineDirection.value,
                            deliveryCategories = categoriesToSend
                        )
                    )
                }

                result.fold(
                    onSuccess = { emit(AdCategoryInitState.Ready(it)) },
                    onFailure = { emit(AdCategoryInitState.Error(it.message ?: "Unknown error")) }
                )
            }.flowOn(Dispatchers.IO)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AdCategoryInitState.Loading
        )



    // ----------- 🚀 from server value -----------


    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateSelectedCategory(deliveryCategory: DeliveryCategory) = run {
        _selectedCategory.value = deliveryCategory
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    fun favorite(advertisementId: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val result = favoriteRepository.favorite(advertisementId)
        }
    }
}


