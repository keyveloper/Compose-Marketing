package com.example.marketing.domain

import com.example.marketing.enums.ReviewOfferStatus

data class AdParticipatedByInfluencer(
    val advertisementId: Long,
    val offerStatus: ReviewOfferStatus,
    val thumbnailUrl: String?
)