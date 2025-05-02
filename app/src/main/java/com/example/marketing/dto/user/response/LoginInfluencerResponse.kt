package com.example.marketing.dto.user.response

data class LoginInfluencerResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: LoginInfluencerResult
)