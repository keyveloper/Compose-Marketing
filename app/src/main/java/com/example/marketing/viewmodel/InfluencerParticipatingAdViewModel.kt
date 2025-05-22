package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdParticipatedByInfluencer
import com.example.marketing.enums.InfluencerParticipatingViewTabState
import com.example.marketing.repository.ReviewOfferRepository
import com.example.marketing.state.InfluencerParticipatingAdInitState
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
import javax.inject.Inject

@HiltViewModel
class InfluencerParticipatingAdViewModel @Inject constructor(
    private val reviewOfferRepository: ReviewOfferRepository
): ViewModel() {
    // -----------   🔃 refresh    -----------
    private val refresh = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }
    fun refresh() { refresh.tryEmit(Unit) }

    // ------------🔃 status      -------------
    private val _influencerParticipatingViewTabState =
        MutableStateFlow(InfluencerParticipatingViewTabState.OFFERING)
    val influencerParticipatingViewTabState = _influencerParticipatingViewTabState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val initState: StateFlow<InfluencerParticipatingAdInitState> = refresh
        .flatMapLatest {
            flow {
                emit(InfluencerParticipatingAdInitState.Loading)

                val result = runCatching {
                    reviewOfferRepository.fetchAllAdsParticipatedByInfluencer()
                }

                result.fold(
                    onSuccess = { emit(InfluencerParticipatingAdInitState.Ready(it)) },
                    onFailure = { emit(InfluencerParticipatingAdInitState.Error(
                        it.message ?: "Unknown error")
                    )
                    }
                )
            }.flowOn(Dispatchers.IO)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = InfluencerParticipatingAdInitState.Loading
        )

    // ----------- 🚀 from server value -----------

    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateInfluencerOwnedTabStatus(status: InfluencerParticipatingViewTabState) = run {
        _influencerParticipatingViewTabState.value = status


    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    }
}