package com.example.marketing.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketing.domain.AdvertisementDraft
import com.example.marketing.domain.WritingImageItem
import com.example.marketing.dto.board.request.AdvertisementCommonFields
import com.example.marketing.dto.board.request.AdvertisementWithKeyword
import com.example.marketing.dto.board.response.IssueNewAdvertisementDraftResponse
import com.example.marketing.enums.AdWritingStatus
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.ReviewType
import com.example.marketing.exception.BusinessException
import com.example.marketing.repository.AdvertisementDraftRepository
import com.example.marketing.repository.AdvertisementImageRepository
import com.example.marketing.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertisementWritingViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val advertisementDraftRepository: AdvertisementDraftRepository,
): ViewModel() {
    // --------------- DRAFT META DATA FOR SAVING -----------------
    private val _draft = MutableStateFlow<AdvertisementDraft?>(null)
    val draft = _draft.asStateFlow()
    
    private val _writingStatus = MutableStateFlow(AdWritingStatus.INIT)
    val writingStatus = _writingStatus.asStateFlow()

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

    private val _categories = MutableStateFlow(listOf<DeliveryCategory>())
    val categories = _categories.asStateFlow()

    // ------------------ Image & keyword  ------------------
    private val _thumbnail = MutableStateFlow<WritingImageItem?> (null)
    val thumbnail = _thumbnail.asStateFlow()

    private val _imageItems = MutableStateFlow (listOf<WritingImageItem>())
    val imageItems = _imageItems.asStateFlow()

    private val _keywords = MutableStateFlow (setOf<String>())
    val keywords = _keywords.asStateFlow()

    // ------------------ API STATUS -----------------------
    private val _saveApiStatus = MutableStateFlow(false)
    val saveApiStatus = _saveApiStatus.asStateFlow()

    // ------------------ UPDATE METHODS ------------------
    private fun updateDraft(draft: AdvertisementDraft) = run { _draft.value = draft }

    fun updateWritingStatus(status: AdWritingStatus) = run { _writingStatus.value = status }

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

    fun updateThumbnail(item: WritingImageItem?) = run { _thumbnail.value = item }

    fun updateSaveApiStatus(isSaved: Boolean) = run { _saveApiStatus.value = isSaved }

    fun addImageUris(items: List<WritingImageItem>) = run { _imageItems.update { it + items }}

    fun addImageItem(item: WritingImageItem) = run { _imageItems.update { it + item } }

    fun addCategory(category: DeliveryCategory) = run {
        _categories.update { it + category}
        Log.i("adWritingViewModel", "now items: ${categories.value}")
    }

    fun deleteCategory(category: DeliveryCategory) = run { _categories.value -= category }

    fun deleteImageItemByUri(targetUri: Uri) = run {
        _imageItems.update { current ->
            current.filterNot { it.localUri == targetUri }
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

    fun checkEssentialFields(): Boolean {
        return _thumbnail.value != null
    }

    fun resetPage() {
        _draft.value = null
        _writingStatus.value = AdWritingStatus.INIT

        _title.value = ""
        _itemName.value = ""
        _itemInfo.value = null
        _siteUrl.value = null

        _channelType.value = ChannelType.BLOGGER
        _reviewType.value = ReviewType.BUY

        _recruitmentNumber.value = null
        _recruitStartAt.value = null
        _recruitEndAt.value = null
        _announcementAt.value = null
        _reviewStartAt.value = null
        _reviewEndAt.value = null

        _categories.value = emptyList()
        _thumbnail.value = null
        _imageItems.value = emptyList()
        _keywords.value = emptySet()

        _saveApiStatus.value = false
    }

    // ------------------ API METHODS ------------------
    // 📂 Draft
    suspend fun issueDraft() {
        viewModelScope.launch(Dispatchers.IO) {
            val domain = advertisementDraftRepository.issue()

            if (domain == null) {
                Log.i("adWritingVm", "new Draft not fetched... something is wrong ")
            } else {
                withContext(Dispatchers.Main) {
                    updateDraft(domain)
                    updateWritingStatus(AdWritingStatus.DRAFT_ISSUED)
                }
            }
        }
    }

    suspend fun uploadImage(uri: Uri) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                addImageItem(
                    WritingImageItem.of(
                        localUri = uri,
                        domain = null,
                        uploaded = false
                    )
                )

                val domain = advertisementImageRepository.uploadImage(uri, draft.value!!.id)
                Log.i("adWritingVm", "uploadImg-domain(result: $domain")

                withContext(Dispatchers.Main) {
                    val updatedList = _imageItems.value.map { item ->
                        if (item.localUri == uri) item.copy(
                            domain = domain,
                            uploaded = true
                        ) else item
                    }

                    _imageItems.value = updatedList
                    val updatedItem = _imageItems.value.find { it.localUri == uri }
                    Log.i("adWritingVm", "updatedItem = $updatedItem")
                }
            }
        } catch (ex: BusinessException) {
            Log.i("adWritingViewModel", ex.message)
        }
    }

    suspend fun withdrawUploadingImage(item: WritingImageItem?) {
        try {
            if (item == null) {
                Log.i("adWritingViewModel", "withdrawUploadingImage- why item is null??")
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                advertisementImageRepository.withdrawUpload(item.domain!!.entityId) // 🤔 always non-null?

                withContext(Dispatchers.Main) {
                    deleteImageItemByUri(item.localUri)
                }
            }
        } catch (ex: BusinessException) {
            Log.i("adWritingViewModel", ex.message)
        }
    }

    suspend fun uploadAdvertisement() {
        val valid = checkEssentialFields()

        if (!valid) {
            Log.i("adWritingViewModel", "essential fields must filled")
        } else {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    val adId =  advertisementRepository.upload(
                        AdvertisementWithKeyword(
                            AdvertisementCommonFields(
                                title = _title.value,
                                reviewType = _reviewType.value,
                                channelType = _channelType.value,
                                itemName = _itemName.value,
                                itemInfo = _itemInfo.value,
                                recruitmentNumber = _recruitmentNumber.value ?: 0,
                                recruitmentStartAt = _recruitStartAt.value ?: 0L,
                                recruitmentEndAt = _recruitEndAt.value ?: 0L,
                                announcementAt = _announcementAt.value ?: 0L,
                                reviewStartAt = _reviewStartAt.value ?: 0L,
                                reviewEndAt = _reviewEndAt.value ?: 0L,
                                endAt = _reviewEndAt.value ?: 0L,
                                siteUrl = _siteUrl.value,
                                draftId = _draft.value?.id ?: -1L
                            ),
                            categories = _categories.value,
                            keywords = _keywords.value
                        )
                    )

                    val setAdvertisementId = advertisementImageRepository.setAdvertisementId(
                        _draft.value!!.id
                    )

                    if (setAdvertisementId) {
                        advertisementImageRepository.setThumbnail(
                            _thumbnail.value!!.domain!!.entityId, adId)

                        withContext(Dispatchers.Main) {
                            updateSaveApiStatus(true)
                        }
                    }
                }

            } catch (ex: BusinessException) {
                Log.i("adWritingViewModel", ex.message)
            }
        }
    }
}