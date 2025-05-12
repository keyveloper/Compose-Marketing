package com.example.marketing.dto.user.response

data class MakeNewAdvertiserProfileInfoResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdId: Long
)
