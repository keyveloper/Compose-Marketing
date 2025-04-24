package com.example.marketing.ui.component

import com.example.marketing.domain.Advertisement
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType

data class AdvertisementThumbnailItem(
    val advertisementId: Long,
    val thumbnailImageUrl: String?,
    val title: String,
    val itemName: String,
    val channelType: ChannelType,
    val reviewType: ReviewType,
) {
    companion object {
        fun of(
            advertisement: Advertisement
        ): AdvertisementThumbnailItem {
            return AdvertisementThumbnailItem(
                advertisementId = advertisement.advertisementGeneral.id,
                thumbnailImageUrl = null,
                title = advertisement.advertisementGeneral.title,
                itemName = advertisement.advertisementGeneral.itemName,
                channelType = advertisement.advertisementGeneral.channelType,
                reviewType = advertisement.advertisementGeneral.reviewType
            )
        }
    }
}