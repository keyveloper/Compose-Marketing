package com.example.marketing.ui.component

import com.example.marketing.domain.AdvertisementGeneralFields
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType

data class AdvertisementThumbnailItem(
    val advertisementId: Long,
    val thumbnailUri: String?,
    val title: String,
    val itemName: String,
    val channelType: ChannelType,
    val reviewType: ReviewType,
    val imageBytes: ByteArray?
) {
    companion object {
        fun of(
            generalFields: AdvertisementGeneralFields,
            imageBytes: ByteArray?
        ): AdvertisementThumbnailItem = AdvertisementThumbnailItem(
            advertisementId = generalFields.id,
            thumbnailUri = generalFields.thumbnailUri,
            title = generalFields.title,
            itemName = generalFields.itemName,
            channelType = generalFields.channelType,
            reviewType = generalFields.reviewType,
            imageBytes = imageBytes
        )
    }
}