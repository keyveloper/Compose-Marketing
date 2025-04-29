package com.example.marketing.dto.functions.response

data class OfferingInfluencerInfo(
    val influencerId: Long,
    val influencerLoginId: String,
    val influencerMainProfileImageUrl: String?,
    val offer: String,
    val offerCreatedAt: Long,
)