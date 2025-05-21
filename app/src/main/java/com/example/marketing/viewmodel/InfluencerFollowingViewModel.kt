package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.functions.request.OfferReview
import com.example.marketing.dto.functions.response.OfferingInfluencerInfo
import com.example.marketing.repository.FavoriteRepository
import com.example.marketing.repository.FollowRepository
import com.example.marketing.repository.ReviewOfferRepository
import com.example.marketing.state.FollowingFeedFetchState
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerFollowingViewModel @Inject constructor(
    private val followRepository: FollowRepository,
    private val favoriteRepository: FavoriteRepository,
    private val reviewOfferRepository: ReviewOfferRepository
): ViewModel() {
    // ------------⛏️ init value -------------
    val refresh = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val initFeedFetchSTate: StateFlow<FollowingFeedFetchState> =
        refresh.flatMapLatest {
            flow {
                emit(FollowingFeedFetchState.Loading)

                val result = runCatching {
                    val feeds = followRepository.fetchAdsByInfluencerId()
                    feeds.sortedByDescending { it.advertisementCreatedAt }
                    feeds
                }

                result.fold(
                    onSuccess = { emit(FollowingFeedFetchState.Ready(it)) },
                    onFailure = { emit(FollowingFeedFetchState.Error(
                        it.message ?: "Init Feed fetch error ..."
                    ))}
                )
            }.flowOn(Dispatchers.IO)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = FollowingFeedFetchState.Loading
            )
    // ------------✍️ input value -------------
    // ------------🔃 status ------------

    // ----------- 🚀 from server value -----------
    private val _offerInfos = MutableStateFlow(listOf<OfferingInfluencerInfo>())
    val offerInfos = _offerInfos.asStateFlow()

    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    private fun addOfferInfos(offerInfos: List<OfferingInfluencerInfo>) {
        _offerInfos.value += offerInfos
    }

    private fun setOfferInfos() {
        _offerInfos.value = listOf()
    }

    // -------------🔍 inspection -----------


    // ----------- 🛜 API -----------------------
    fun favoriteAd(advertisementId: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val result = favoriteRepository.favorite(advertisementId)
        }
    }

    fun fetchOfferInfos(advertisementId: Long) = viewModelScope.launch {

        withContext(Dispatchers.IO) {
            val infos = reviewOfferRepository.fetchOfferingInfluencerInfos(
                advertisementId
            )

            withContext(Dispatchers.Main) {
                setOfferInfos()
                addOfferInfos(infos)
            }
        }
    }


}