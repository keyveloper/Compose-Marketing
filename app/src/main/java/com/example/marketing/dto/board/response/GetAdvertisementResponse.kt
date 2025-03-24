package com.example.marketing.dto.board.response

import com.example.marketing.domain.Advertisement

data class GetAdvertisementResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val advertisement: Advertisement
) {
}