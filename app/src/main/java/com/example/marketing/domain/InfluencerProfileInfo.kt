package com.example.marketing.domain

data class InfluencerProfileInfo(
    val id: Long,
    val influencerId: Long,
    val introduction: String?,
    val job: String?,
    val createdAt: Long,
    val updatedAt: Long,
)