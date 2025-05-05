package com.example.marketing.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.repository.AdvertisementImageRepository
import com.example.marketing.repository.AdvertisementRepository
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
    savedStateHandle: SavedStateHandle
): ViewModel() {
    // ----------- ⛏️ init value (from route)  ----------
    private val advertisementId: Long = checkNotNull(savedStateHandle["advertisementId"])
    
    private val _advertisementPackage = MutableStateFlow<AdvertisementPackage?>(null)
    val advertisementPackage = _advertisementPackage.asStateFlow()

    // ----------- 🎮 update  ----------
    fun updateAdvertisementPackage(pkg: AdvertisementPackage) = run {
        _advertisementPackage.value = pkg
    }

    private fun addImageBytes(bytes: List<ByteArray>) = run {
        _imageBytesList.value += bytes
    }

    // ----------- 🚀 API data  ----------
    private val _imageBytesList = MutableStateFlow<List<ByteArray>>(emptyList())
    val imageBytesList: StateFlow<List<ByteArray>> = _imageBytesList

    // ----------- 🛜 API function -----------
    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pkg = advertisementRepository.fetchById(advertisementId)

                val uris = pkg!!.advertisementGeneralFields.imageUris

                val deferreds = uris.map { uri ->
                    async(Dispatchers.IO) { advertisementImageRepository.fetchImageBytes(uri) }
                }

                // awaitAll gives List<ByteArray?> if fetchImageBytes returns ByteArray?
                val results: List<ByteArray?> = deferreds.awaitAll()
                // Filter out any nulls
                val bytesList: List<ByteArray> = results.filterNotNull()

                withContext(Dispatchers.Main) {
                    _imageBytesList.value = bytesList
                    updateAdvertisementPackage(pkg)
                }
            } catch (t: Throwable) {

            }
        }
    }
    fun fetchImage(uri: String) = viewModelScope.launch(Dispatchers.IO) {
        
    }
}