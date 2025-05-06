package com.example.marketing.dto.user.response

import com.example.marketing.domain.InfluencerJoinedProfileInfo

data class GetInfluencerProfileResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: InfluencerJoinedProfileInfo
)