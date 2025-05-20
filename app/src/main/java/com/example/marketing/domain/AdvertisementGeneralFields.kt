package com.example.marketing.domain

import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType

data class AdvertisementGeneralFields(
    val id: Long,
    val title: String,
    val reviewType: ReviewType,
    val advertiserId: Long,
    val advertiserLoginId: String,
    val channelType: ChannelType,
    val recruitmentNumber: Int,
    val itemName: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val announcementAt: Long,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val endAt: Long,
    val siteUrl: String?,
    val itemInfo: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val imageUris: List<String?>,
    val thumbnailUri: String?,
)