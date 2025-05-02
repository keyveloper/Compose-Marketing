package com.example.marketing.dto.user.response

data class LoginInfluencerResult(
    val jwt: String,
    val influencerId: Long
)