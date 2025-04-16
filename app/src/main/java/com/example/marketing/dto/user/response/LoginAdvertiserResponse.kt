package com.example.marketing.dto.user.response

data class LoginAdvertiserResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val jwt: String,
    val advertiserId: Long
)