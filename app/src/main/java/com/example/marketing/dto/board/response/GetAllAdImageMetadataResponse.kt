package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementImage

data class GetAllAdImageMetadataResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: List<AdvertisementImage>
)