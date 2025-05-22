package com.example.marketing.dto.functions.response

import com.example.marketing.domain.AdParticipatedByInfluencer

data class GetAdsParticipatedByInfluencerResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: List<AdParticipatedByInfluencer>
)