package com.example.marketing.domain

import com.example.marketing.enums.ReviewOfferStatus

data class InfluencerValidReviewOfferAd(
    val advertisementId: Long,
    val offerStatus: ReviewOfferStatus,
    val thumbnailUrl: String?
)