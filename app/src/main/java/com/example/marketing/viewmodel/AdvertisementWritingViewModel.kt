package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AdvertisementWritingViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository
): ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    fun updateTitle(value: String) {
        _title.value = value
    }

    private val _channelType = MutableStateFlow(ChannelType.BLOGGER)
    val channelType = _channelType.asStateFlow()
    fun updateChannelType(value: ChannelType) {
        _channelType.value = value
    }

    private val _reviewType = MutableStateFlow(ReviewType.BUY)
    val reviewType = _reviewType.asStateFlow()
    fun updateReviewType(value: ReviewType) {
        _reviewType.value = value
    }

    private val _itemName = MutableStateFlow("")
    val itemName = _itemName.asStateFlow()
    fun updateItemName(value: String) {
        _itemName.value = value
    }

    private val _itemInfo = MutableStateFlow<String?>(null)
    val itemInfo: StateFlow<String?> = _itemInfo.asStateFlow()
    fun updateItemInfo(value: String?) {
        _itemInfo.value = value
    }

    private val _recruitmentNumber = MutableStateFlow<Int?>(null)
    val recruitmentNumber: StateFlow<Int?> = _recruitmentNumber.asStateFlow()
    fun updateRecruitmentNumber(value: Int?) {
        _recruitmentNumber.value = value
    }

    private val _recruitStartAt = MutableStateFlow<Long?>(null)
    val recruitStartAt: StateFlow<Long?> = _recruitStartAt.asStateFlow()
    fun updateRecruitStartAt(value: Long?) {
        _recruitStartAt.value = value
    }

    private val _recruitEndAt = MutableStateFlow<Long?>(null)
    val recruitEndAt: StateFlow<Long?> = _recruitEndAt.asStateFlow()
    fun updateRecruitEndAt(value: Long?) {
        _recruitEndAt.value = value
    }

    private val _announcementAt = MutableStateFlow<Long?>(null)
    val announcementAt: StateFlow<Long?> = _announcementAt.asStateFlow()
    fun updateAnnouncementAt(value: Long?) {
        _announcementAt.value = value
    }

    private val _reviewStartAt = MutableStateFlow<Long?>(null)
    val reviewStartAt: StateFlow<Long?> = _reviewStartAt.asStateFlow()
    fun updateReviewStartAt(value: Long?) {
        _reviewStartAt.value = value
    }

    private val _reviewEndAt = MutableStateFlow<Long?>(null)
    val reviewEndAt: StateFlow<Long?> = _reviewEndAt.asStateFlow()
    fun updateReviewEndAt(value: Long?) {
        _reviewEndAt.value = value
    }

    private val _siteUrl = MutableStateFlow<String?>(null)
    val siteUrl: StateFlow<String?> = _siteUrl.asStateFlow()
    fun updateSiteUrl(value: String?) {
        _siteUrl.value = value
    }
}