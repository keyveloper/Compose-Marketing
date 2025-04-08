package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.repository.AdvertiseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdvertiserLoginViewModel @Inject constructor(
    advertiserRepository: AdvertiseRepository
): ViewModel() {

}