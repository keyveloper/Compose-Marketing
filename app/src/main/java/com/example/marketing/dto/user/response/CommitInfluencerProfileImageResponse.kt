package com.example.marketing.dto.user.response

import com.example.marketing.domain.InfluencerProfileImage

data class CommitInfluencerProfileImageResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: InfluencerProfileImage
)