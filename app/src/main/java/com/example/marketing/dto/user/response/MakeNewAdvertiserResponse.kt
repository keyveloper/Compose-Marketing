package com.example.marketing.dto.user.response

data class MakeNewAdvertiserResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdId: Long,
)