package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.domain.Influencer
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InfluencerProfileViewModel @Inject constructor (
    private val influencerRepository: InfluencerRepository
): ViewModel() {

    private val _influencer = MutableStateFlow<Influencer?> (null)
    val influencer: StateFlow<Influencer?> = _influencer.asStateFlow()


}