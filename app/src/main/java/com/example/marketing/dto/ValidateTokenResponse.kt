package com.example.marketing.dto

data class ValidateTokenResponse(
    val validateResult: ValidateTokenResult,
    val frontErrorCode: Int,
    val errorMessage: String
)