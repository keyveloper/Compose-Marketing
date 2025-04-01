package com.example.marketing.dto.board.response

import com.example.marketing.domain.Advertisement

data class GetAdvertisementFreshResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val advertisements: List<Advertisement>
)