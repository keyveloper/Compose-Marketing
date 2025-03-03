package com.example.marketing.dto.user.response

data class LoginAdminResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val adminId: Long,
    val token: String,
)