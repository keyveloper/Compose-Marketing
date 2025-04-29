package com.example.marketing.dto.functions.response

data class FollowAdvertiserResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: FollowAdvertiserResult
)