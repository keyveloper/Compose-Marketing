package com.example.marketing.dto.board.response

data class MakeNewAdvertisementImageResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: MakeNewAdvertisementImageResult
)
