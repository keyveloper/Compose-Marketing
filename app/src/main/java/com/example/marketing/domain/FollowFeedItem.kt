package com.example.marketing.domain

import com.example.marketing.enums.AdvertiserType

data class FollowFeedItem(
    val advertiserId: Long,
    val advertiserLoginId: String,
    val adImageUris: List<String?>,
    val advertisementId: Long,
    val itemName: String,
    val itemIfo: String?,
    val advertiserProfileUnifiedCode: String?,
    val companyName: String,
    val advertiserType: AdvertiserType,
    val advertisementCreatedAt: Long,
) {
    companion object {
        fun of(
            generalFields: AdvertisementGeneralFields,
            advertiserInfo: AdvertiserSummaryForFollow
        ): FollowFeedItem = FollowFeedItem(
            advertiserId = generalFields.advertiserId,
            advertiserLoginId = generalFields.advertiserLoginId,
            adImageUris = generalFields.imageUris,
            advertisementId = generalFields.id,
            itemName = generalFields.itemName,
            itemIfo = generalFields.itemInfo,
            advertiserProfileUnifiedCode = advertiserInfo.advertiserProfileUnifiedCode,
            companyName = advertiserInfo.companyName,
            advertiserType = advertiserInfo.advertiserType,
            advertisementCreatedAt = generalFields.createdAt
        )
    }
}
