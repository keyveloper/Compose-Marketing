package com.example.marketing.dto.functions.response

import com.example.marketing.domain.InfluencerValidReviewOfferAd

data class GetValidReviewOfferAdResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: List<InfluencerValidReviewOfferAd>
)