package com.example.marketing.ui.component

import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType

data class AdvertisementThumbnailItem(
    val advertisementId: Long,
    val thumbnailImageUrl: String?,
    val title: String,
    val itemName: String,
    val channelType: ChannelType,
    val reviewType: ReviewType,
)