package com.example.marketing.dto.board.response

data class MakeNewAdvertisementGeneralResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdId: Long
)