package com.example.marketing.dto.user.response

data class MakeNewAdminResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val createdId: Long
)