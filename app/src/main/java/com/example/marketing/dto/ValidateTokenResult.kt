package com.example.marketing.dto

import com.example.marketing.enums.UserType

data class ValidateTokenResult(
    val userId: Long?,
    val userType: UserType,
    val onSuccess: Boolean,
)