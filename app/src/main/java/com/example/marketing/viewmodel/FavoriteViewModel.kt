package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.FavoriteViewStatus
import com.example.marketing.view.FavoriteScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(

): ViewModel() {
    // ----------- 📌 fixed ---------

    // ------------✍️ input value -------------
    // ------------🔃 status ------------
    private val _favoriteViewStatus = MutableStateFlow (FavoriteViewStatus.MY_OFFER)
    val favoriteViewStatus = _favoriteViewStatus.asStateFlow()

    // ----------- 🚀 from server value -----------

    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateViewStatus(status: FavoriteViewStatus) = run {
        _favoriteViewStatus.value = status
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
}