package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.InfluencerFavoriteAdWithThumbnail
import com.example.marketing.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerFavoriteAdViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    // ------------✍️ input value -------------
    // ------------🔃 status ------------
    // ----------- 🚀 from server value -----------
    private val _favoriteAdItems = MutableStateFlow (listOf<InfluencerFavoriteAdWithThumbnail>())
    val favoriteAdItems = _favoriteAdItems.asStateFlow()

    // ---------------- [Function] ---------------
    // ----------- 🎮 update function--------
    private fun addFavoriteAdItems(items: List<InfluencerFavoriteAdWithThumbnail>) = run {
        _favoriteAdItems.value += items
    }

    private fun setFavoriteAdItems() = run {
        _favoriteAdItems.value = listOf()
    }
    // -------------🔍 inspection -----------
    // ----------- 🛜 API --------------------
    fun fetchFavoriteAds() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val result = favoriteRepository.fetchAllAdsWithThumbnail()

            if (result.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    setFavoriteAdItems()
                    addFavoriteAdItems(result)
                }
            }
        }
    }
}