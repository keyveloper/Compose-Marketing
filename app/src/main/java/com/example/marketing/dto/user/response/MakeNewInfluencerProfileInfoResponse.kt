package com.example.marketing.dto.user.response

data class MakeNewInfluencerProfileInfoResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdId: Long
)