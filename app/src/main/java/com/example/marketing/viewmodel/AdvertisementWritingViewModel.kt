package com.example.marketing.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.repository.AdvertisementRepository
import com.example.marketing.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AdvertisementWritingViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
    private val imageRepository: ImageRepository
): ViewModel() {

    // ------------------ BASIC TEXT INPUTS ------------------
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _itemName = MutableStateFlow("")
    val itemName: StateFlow<String> = _itemName.asStateFlow()

    private val _itemInfo = MutableStateFlow<String?>(null)
    val itemInfo: StateFlow<String?> = _itemInfo.asStateFlow()

    private val _siteUrl = MutableStateFlow<String?>(null)
    val siteUrl: StateFlow<String?> = _siteUrl.asStateFlow()

    // ------------------ CHANNEL & REVIEW ------------------
    private val _channelType = MutableStateFlow(ChannelType.BLOGGER)
    val channelType: StateFlow<ChannelType> = _channelType.asStateFlow()

    private val _reviewType = MutableStateFlow(ReviewType.BUY)
    val reviewType: StateFlow<ReviewType> = _reviewType.asStateFlow()

    // ------------------ RECRUITMENT & SCHEDULING ------------------
    private val _recruitmentNumber = MutableStateFlow<Int?>(null)
    val recruitmentNumber: StateFlow<Int?> = _recruitmentNumber.asStateFlow()

    private val _recruitStartAt = MutableStateFlow<Long?>(null)
    val recruitStartAt: StateFlow<Long?> = _recruitStartAt.asStateFlow()

    private val _recruitEndAt = MutableStateFlow<Long?>(null)
    val recruitEndAt: StateFlow<Long?> = _recruitEndAt.asStateFlow()

    private val _announcementAt = MutableStateFlow<Long?>(null)
    val announcementAt: StateFlow<Long?> = _announcementAt.asStateFlow()

    private val _reviewStartAt = MutableStateFlow<Long?>(null)
    val reviewStartAt: StateFlow<Long?> = _reviewStartAt.asStateFlow()

    private val _reviewEndAt = MutableStateFlow<Long?>(null)
    val reviewEndAt: StateFlow<Long?> = _reviewEndAt.asStateFlow()

    // ------------------ Image & keyword  ------------------
    private val _imageUris = MutableStateFlow (listOf<Uri>())
    val imageUris = _imageUris.asStateFlow()

    private val _keywords = MutableStateFlow (setOf<String>())
    val keywords = _keywords.asStateFlow()

    // ------------------ UPDATE METHODS ------------------

    fun updateTitle(value: String) = run { _title.value = value }

    fun updateItemName(value: String) = run { _itemName.value = value }

    fun updateItemInfo(value: String?) = run { _itemInfo.value = value }

    fun updateSiteUrl(value: String?) = run { _siteUrl.value = value }

    fun updateChannelType(value: ChannelType) = run { _channelType.value = value }

    fun updateReviewType(value: ReviewType) = run { _reviewType.value = value }

    fun updateRecruitmentNumber(value: Int?) = run { _recruitmentNumber.value = value }

    fun updateRecruitStartAt(value: Long?) = run { _recruitStartAt.value = value }

    fun updateRecruitEndAt(value: Long?) = run { _recruitEndAt.value = value }

    fun updateAnnouncementAt(value: Long?) = run { _announcementAt.value = value }

    fun updateReviewStartAt(value: Long?) = run { _reviewStartAt.value = value }

    fun updateReviewEndAt(value: Long?) = run { _reviewEndAt.value = value }

    fun addImageUris(uris: List<Uri>) = run { _imageUris.update { it + uris }}

    fun deleteImageUri(targetUri: Uri?) = run {
        if (targetUri != null) {
            val current = _imageUris.value.toMutableList()
            current.remove(targetUri)
            _imageUris.value = current
        }
    }

    fun addKeyword(keyword: String) = run {
        if (keyword.isNotBlank()) {
            _keywords.value += keyword
        }
    }

    fun deleteKeyword(keyword: String) = run {
        _keywords.value -= keyword
    }

    fun upload() {
        

    }

    fun checkEssentialFields(): Boolean {
        return true
    }
}