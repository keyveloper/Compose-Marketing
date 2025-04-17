package com.example.marketing.dto.user.response

import com.example.marketing.domain.AdvertiserProfileResult


data class AdvertiserProfileResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: AdvertiserProfileResult
)