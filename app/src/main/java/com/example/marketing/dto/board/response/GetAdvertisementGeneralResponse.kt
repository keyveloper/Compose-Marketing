package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementPackage

data class GetAdvertisementGeneralResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val advertisement: AdvertisementPackage
)
