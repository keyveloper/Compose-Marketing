package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementImage

data class MakeNewAdvertisementImageResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: AdvertisementImage
)
