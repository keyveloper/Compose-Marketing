package com.example.marketing.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.functions.request.OfferReview
import com.example.marketing.dto.functions.response.OfferingInfluencerInfo
import com.example.marketing.enums.UserType
import com.example.marketing.repository.AdvertisementImageRepository
import com.example.marketing.repository.AdvertisementRepository
import com.example.marketing.repository.ReviewOfferRepository
import com.example.marketing.state.AdDetailInitFetchedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertisementDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val advertisementRepository: AdvertisementRepository,
    private val reviewOfferRepository: ReviewOfferRepository
): ViewModel() {
    // ----------- ⛏️ init value (from route)  ---------
    private val refresh = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    private val _userTypeStr =
        savedStateHandle.getStateFlow("userType", UserType.VISITOR.name)
    val userType: StateFlow<UserType> = _userTypeStr
        .map { enumValueOf<UserType>(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserType.VISITOR)

    private val _advertisementId =
        savedStateHandle.getStateFlow("advertisementId",   null)
    val advertisementId: StateFlow<Long?> = _advertisementId

    @OptIn(ExperimentalCoroutinesApi::class)
    val initFetchedState: StateFlow<AdDetailInitFetchedState> = advertisementId
        .filter { it != null }
        .distinctUntilChanged()
        .flatMapLatest { id ->
            refresh.flatMapLatest {
                flow {
                    emit(AdDetailInitFetchedState.Loading)
                    val pkg = advertisementRepository.fetchById(id!!)
                        ?: return@flow emit(AdDetailInitFetchedState.Error("Not Found..."))

                    emit(AdDetailInitFetchedState.Ready(
                        pkg
                    ))
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AdDetailInitFetchedState.Loading
        )

    // ------------✍️ input value -------------
    private val _inputOffer = MutableStateFlow("")
    val inputOffer = _inputOffer.asStateFlow()

    // ----------- 🎮 update  ----------
    fun updateInputOffer(offer: String) = run {
        _inputOffer.value = offer
    }


    private fun addInfluencerInfos(infos: List<OfferingInfluencerInfo>) = run {
        _influencerInfos.value += infos
    }

    // ----------- 🚀 API data  ----------
    fun refresh() { refresh.tryEmit(Unit) }

    private val _influencerInfos = MutableStateFlow<List<OfferingInfluencerInfo>>(emptyList())
    val influencerInfos = _influencerInfos.asStateFlow()

    // ----------- 🛜 API function -----------

    fun fetchOffers() = viewModelScope.launch {

        withContext(Dispatchers.IO) {
            val infos = reviewOfferRepository.fetchOfferingInfluencerInfos(
                advertisementId = advertisementId.value ?: -1L
            )

            withContext(Dispatchers.Main) {
                _influencerInfos.value = listOf()
                addInfluencerInfos(infos)
            }
        }
    }

    fun offer() = viewModelScope.launch {
        if (_influencerInfos.value.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                val createdId = reviewOfferRepository.offer(
                    OfferReview.of(
                        offer = _inputOffer.value,
                        advertisementId = _advertisementId.value ?: -1L
                    )
                )
            }
        }
    }
}