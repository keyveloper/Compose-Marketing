package com.example.marketing.dto.user.response

import com.example.marketing.domain.AdvertiserProfileInfo


data class GetAdvertiserProfileResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: AdvertiserProfileInfo
)