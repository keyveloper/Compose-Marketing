package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketing.domain.Advertisement
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdvertisementDetailViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository
): ViewModel() {

    private val _id = MutableStateFlow(-1L)
    val id: StateFlow<Long> = _id.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _reviewType = MutableStateFlow(ReviewType.VISITED) // 기본값 설정 (필요에 따라 변경)
    val reviewType: StateFlow<ReviewType> = _reviewType.asStateFlow()

    private val _channelType = MutableStateFlow(ChannelType.BLOGGER) // 기본값 설정 (필요에 따라 변경)
    val channelType: StateFlow<ChannelType> = _channelType.asStateFlow()

    private val _recruitmentNumber = MutableStateFlow(0)
    val recruitmentNumber: StateFlow<Int> = _recruitmentNumber.asStateFlow()

    private val _itemName = MutableStateFlow("")
    val itemName: StateFlow<String> = _itemName.asStateFlow()

    private val _recruitmentStartAt = MutableStateFlow("")
    val recruitmentStartAt: StateFlow<String> = _recruitmentStartAt.asStateFlow()

    private val _recruitmentEndAt = MutableStateFlow("")
    val recruitmentEndAt: StateFlow<String> = _recruitmentEndAt.asStateFlow()

    private val _announcementAt = MutableStateFlow("")
    val announcementAt: StateFlow<String> = _announcementAt.asStateFlow()

    private val _reviewStartAt = MutableStateFlow("")
    val reviewStartAt: StateFlow<String> = _reviewStartAt.asStateFlow()

    private val _reviewEndAt = MutableStateFlow("")
    val reviewEndAt: StateFlow<String> = _reviewEndAt.asStateFlow()

    private val _endAt = MutableStateFlow("")
    val endAt: StateFlow<String> = _endAt.asStateFlow()

    private val _siteUrl = MutableStateFlow<String?>(null)
    val siteUrl: StateFlow<String?> = _siteUrl.asStateFlow()

    private val _itemInfo = MutableStateFlow<String?>(null)
    val itemInfo: StateFlow<String?> = _itemInfo.asStateFlow()

    private val _createdAt = MutableStateFlow("")
    val createdAt: StateFlow<String> = _createdAt.asStateFlow()

    private val _updatedAt = MutableStateFlow("")
    val updatedAt: StateFlow<String> = _updatedAt.asStateFlow()

    // 위치 관련 상태 (AdvertisementWithLocation 전용)
    private val _city = MutableStateFlow<String?>(null)
    val city: StateFlow<String?> = _city.asStateFlow()

    private val _district = MutableStateFlow<String?>(null)
    val district: StateFlow<String?> = _district.asStateFlow()

    suspend fun init(id: Long) {
        try {
            // 네트워크 등 I/O 작업은 Dispatchers.IO 사용
            val advertisement = withContext(Dispatchers.IO) {
                advertisementRepository.fetchById(id)
            }


        } catch (e: Exception) {
            throw e
        }}

    fun fetchAdvertisement() {

    }

}