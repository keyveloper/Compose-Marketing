package com.example.marketing.domain

import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.ReviewType

sealed class Advertisement {

    data class AdvertisementGeneral(
        val id: Long,
        val title: String,
        val reviewType: ReviewType,
        val channelType: ChannelType,
        val recruitmentNumber: Int,
        val itemName: String,
        val recruitmentStartAt: String,
        val recruitmentEndAt: String,
        val announcementAt: String,
        val reviewStartAt: String,
        val reviewEndAt: String,
        val endAt: String,
        val siteUrl: String?,
        val itemInfo: String?,
        val createdAt: String,
        val updatedAt: String,
    ) : Advertisement()

    data class AdvertisementDelivery(
        val id: Long,
        val title: String,
        val reviewType: ReviewType,
        val channelType: ChannelType,
        val recruitmentNumber: Int,
        val itemName: String,
        val recruitmentStartAt: String,
        val recruitmentEndAt: String,
        val announcementAt: String,
        val reviewStartAt: String,
        val reviewEndAt: String,
        val endAt: String,
        val siteUrl: String?,
        val itemInfo: String?,
        val createdAt: String,
        val updatedAt: String,
        val categories: List<DeliveryCategory>
    )


    data class AdvertisementWithLocation(
        val locationEntityId: Long,
        val advertisementId: Long,
        val title: String,
        val reviewType: ReviewType,
        val channelType: ChannelType,
        val recruitmentNumber: Int,
        val itemName: String,
        val recruitmentStartAt: String,
        val recruitmentEndAt: String,
        val announcementAt: String,
        val reviewStartAt: String,
        val reviewEndAt: String,
        val endAt: String,
        val siteUrl: String?,
        val itemInfo: String?,
        val city: String?,
        val district: String?,
        val latitude: Double?,
        val longitude: Double?,
        val locationDetails: String,
        val createdAt: String,
        val updatedAt: String
    ) : Advertisement()
}