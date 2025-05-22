package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.EventStatus
import com.example.marketing.enums.UserType
import com.example.marketing.repository.AdvertisementEventRepository
import com.example.marketing.repository.AdvertisementImageRepository
import com.example.marketing.repository.FavoriteRepository
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val advertisementEventRepository: AdvertisementEventRepository,
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    // ------------⛏️ init value -------------
    private val _userId = MutableStateFlow<Long?> (null)
    val userId = _userId.asStateFlow()

    private val _userType = MutableStateFlow<UserType?> (null)
    val userType = _userType.asStateFlow()

    // ------------✍️ input value -------------

    // ------------🔃 status ------------
    private val _eventStatus = MutableStateFlow ( EventStatus.FRESH )
    val eventStatus: StateFlow<EventStatus> = _eventStatus.asStateFlow()

    private val _freshCallStatus = MutableStateFlow ( ApiCallStatus.IDLE )
    val freshCallStatus = _freshCallStatus.asStateFlow()

    private val _deadlineCallStatus = MutableStateFlow ( ApiCallStatus.IDLE )
    val deadlineCallStatus = _deadlineCallStatus.asStateFlow()

    private val _thumbnailCallStatus = MutableStateFlow ( ApiCallStatus.IDLE )
    val thumbnailCallStatus = _thumbnailCallStatus.asStateFlow()

    private val _totalApiCAllStatus = MutableStateFlow ( ApiCallStatus.IDLE )
    val totalApiCategory = _totalApiCAllStatus.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _packages = MutableStateFlow<List<AdvertisementPackage>>( listOf() )
    val packages = _packages.asStateFlow()

    private val _thumbnailItems = MutableStateFlow (listOf<AdvertisementThumbnailItem>())
    val thumbnailItem = _thumbnailItems.asStateFlow()

    // ---------------- <<< Function >>> -----------------------
    // ----------- 🎮 update function-------------
    fun updateUserId(id: Long) = run {
        _userId.value = id
    }

    fun updateUserType(userType: UserType) = run {
        _userType.value = userType
    }

    fun updateEventStatus(status: EventStatus) {
        _eventStatus.value = status
    }

    private fun updateTotalApiCAllStatus(status: ApiCallStatus) = run {
        _totalApiCAllStatus.value = status
    }

    private fun updateDeadlineCallStatus(status: ApiCallStatus) = run {
        _deadlineCallStatus.value = status
    }

    private fun updateFreshCallStatus(status: ApiCallStatus) = run {
        _freshCallStatus.value = status
    }

    private fun updateThumbnailStatus(status: ApiCallStatus) = run {
        _thumbnailCallStatus.value = status  // ✅ Correct
    }


    private fun addPackages(packages: List<AdvertisementPackage>) {
        _packages.value += packages
    }

    private fun addThumbnailItems(items: List<AdvertisementThumbnailItem>) {
        _thumbnailItems.value += items
    }

    private fun clearItems() {
        _packages.value = listOf()
        _thumbnailItems.value = listOf()
    }
    // -------------🔍 inspection -----------
    init {
        combine(freshCallStatus, thumbnailCallStatus) { fresh, thumb ->
            if (fresh == ApiCallStatus.SUCCESS && thumb == ApiCallStatus.SUCCESS)
                ApiCallStatus.SUCCESS else ApiCallStatus.IDLE
        }.onEach { updateTotalApiCAllStatus(it) }
            .launchIn(viewModelScope)
    }

    // ----------- 🛜 API -----------------------

    fun testFetch() = viewModelScope.launch(Dispatchers.IO) {
        clearItems()
        try {
            val pkg = advertisementEventRepository.testFetch()

            withContext(Dispatchers.Main) {
                updateFreshCallStatus(ApiCallStatus.SUCCESS)
                updateThumbnailStatus(ApiCallStatus.SUCCESS)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                updateFreshCallStatus(ApiCallStatus.FAILED)
            }
        }
    }

    fun fetchFreshWithThumbnail() = viewModelScope.launch {
        clearItems()
        withContext(Dispatchers.IO) {
            val packages = advertisementEventRepository.fetchFresh()
            val thumbnailItems = packages.map {
                AdvertisementThumbnailItem.of(
                    generalFields = it.advertisementGeneralFields,
                    unifiedCode =
                    it.advertisementGeneralFields.thumbnailUri?.substringAfterLast('/')
                )
            }

            withContext(Dispatchers.Main) {
                addPackages(packages)
                addThumbnailItems(thumbnailItems)
            }
        }
    }

    suspend fun fetchDeadlineWithThumbnail() {
        clearItems()
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val packages = advertisementEventRepository.fetchDeadline()
                val thumbnailItems = packages.map {
                    AdvertisementThumbnailItem.of(
                        generalFields = it.advertisementGeneralFields,
                        unifiedCode =
                        it.advertisementGeneralFields.thumbnailUri?.substringAfterLast('/')
                    )
                }

                withContext(Dispatchers.Main) {
                    addPackages(packages)
                    addThumbnailItems(thumbnailItems)
                }
            }
        }
    }

    fun favorite(advertisementId: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val result = favoriteRepository.favorite(advertisementId)
        }
    }
}