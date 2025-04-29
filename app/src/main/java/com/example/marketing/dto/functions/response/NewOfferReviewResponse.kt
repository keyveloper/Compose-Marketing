package com.example.marketing.dto.functions.response

data class NewOfferReviewResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdEntityId: Long
)