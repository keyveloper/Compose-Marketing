package com.example.marketing.dto.user.response

import com.example.marketing.domain.AdvertiserProfileImage


data class MakeNewAdvertiserProfileImageResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: AdvertiserProfileImage
)
