package com.example.marketing.dto.board.response

data class GetAdThumbnailUrlResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val url: String,
)