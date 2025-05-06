package com.example.marketing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.dto.functions.response.OfferingInfluencerInfo
import com.example.marketing.repository.AdvertisementImageRepository
import com.example.marketing.repository.AdvertisementRepository
import com.example.marketing.repository.ReviewOfferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertisementDetailViewModel @Inject constructor(
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val advertisementRepository: AdvertisementRepository,
    private val reviewOfferRepository: ReviewOfferRepository
): ViewModel() {
    // ----------- ⛏️ init value (from route)  ----------
    private val _advertisementId = MutableStateFlow<Long?> (null)
    val advertisementId = _advertisementId.asStateFlow()
    
    private val _advertisementPackage = MutableStateFlow<AdvertisementPackage?>(null)
    val advertisementPackage = _advertisementPackage.asStateFlow()

    // ----------- 🎮 update  ----------
    fun updateAdvertisementId(id: Long) = run {
        _advertisementId.value = id
        Log.i("adDetailVm", "updated adId = ${_advertisementId.value}")
    }

    fun updateAdvertisementPackage(pkg: AdvertisementPackage) = run {
        _advertisementPackage.value = pkg
    }

    private fun addImageBytes(bytes: List<ByteArray>) = run {
        _imageBytesList.value += bytes
    }

    private fun addInfluencerInfos(infos: List<OfferingInfluencerInfo>) = run {
        _influencerInfos.value += infos
    }

    // ----------- 🚀 API data  ----------
    private val _imageBytesList = MutableStateFlow<List<ByteArray>>(emptyList())
    val imageBytesList: StateFlow<List<ByteArray>> = _imageBytesList.asStateFlow()

    private val _influencerInfos = MutableStateFlow<List<OfferingInfluencerInfo>>(emptyList())
    val influencerInfo = _influencerInfos.asStateFlow()

    // ----------- 🛜 API function -----------
    fun fetchDetailAndImages() = viewModelScope.launch {
        // Switch to IO for network work
        withContext(Dispatchers.IO) {
            // 1) Fetch the detail
            val pkg: AdvertisementPackage =
                advertisementRepository.fetchById(_advertisementId.value!!)
                ?: // API returned null → update an error state or just return
                // e.g. _errorState.value = "Failed to load advertisement"
                return@withContext

            // 2) Fetch all image bytes in parallel
            val uris = pkg.advertisementGeneralFields.imageUris
            val bytesList: List<ByteArray> = uris.map { uri ->
                async(Dispatchers.IO) {
                    advertisementImageRepository.fetchImageBytes(uri)
                }
            }
                .awaitAll()
                .filterNotNull()  // drop any null returns

            // 3) Back on Main, update your StateFlows
            withContext(Dispatchers.Main) {
                updateAdvertisementPackage(pkg)
                addImageBytes(bytesList)
            }
        }
    }

    fun fetchOffers() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (_advertisementId.value == null) {
                return@withContext
            }
            val infos = reviewOfferRepository.fetchOfferingInfluencerInfos(
                advertisementId = _advertisementId.value!!
            )

            withContext(Dispatchers.Main) {
                addInfluencerInfos(infos)
            }
        }
    }
}