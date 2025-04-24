package com.example.marketing.dto.board.response

data class MakeNewAdvertisementDeliveryResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdId: Long
)

