package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
): ViewModel() {
}