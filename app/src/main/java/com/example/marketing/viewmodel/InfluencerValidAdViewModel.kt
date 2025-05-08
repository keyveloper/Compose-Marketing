package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.InfluencerValidReviewOfferAd
import com.example.marketing.enums.MyOfferStatus
import com.example.marketing.repository.ReviewOfferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerValidAdViewModel @Inject constructor(
    private val reviewOfferRepository: ReviewOfferRepository
): ViewModel() {
    // ------------✍️ input value -------------
    // ------------🔃 status ------------
    private val _myOfferStatus = MutableStateFlow(MyOfferStatus.OFFERING)
    val myOfferStatus = _myOfferStatus.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _validOffers = MutableStateFlow<List<InfluencerValidReviewOfferAd>>(listOf())
    val validOffers = _validOffers.asStateFlow()

    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    private fun addValidOffers(offers: List<InfluencerValidReviewOfferAd>) = run {
        _validOffers.value += offers
    }

    fun updateMyOfferStatus(status: MyOfferStatus) = run {
        _myOfferStatus.value = status
    }

    private fun setValidOffer() = run { _validOffers.value = listOf() }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    fun fetchValliOfferAll() = viewModelScope.launch {
        setValidOffer()
        withContext(Dispatchers.IO) {
            val offers = reviewOfferRepository.fetchAllValidOfferByInfluencerId()

            withContext(Dispatchers.Main) {
                addValidOffers(offers)
            }
        }
    }
}