package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementPackage

data class GetAdvertisementDeadlineResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val packages: List<AdvertisementPackage>
)