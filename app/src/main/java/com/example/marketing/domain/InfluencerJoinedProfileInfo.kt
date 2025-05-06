package com.example.marketing.domain

data class InfluencerJoinedProfileInfo(
    // 😏 Basic info
    val influencerId: Long,
    val influencerLoginId: String,
    val birthday: String,
    val createdAt: Long,
    val blogUrl: String?,
    val instagramUrl: String?,
    val youtuberUrl: String?,
    val threadUrl: String?,
    // 🖼️ img
    val unifiedImageCode: String?,

    // 🧰 additional info
    val job: String?,
    val introduction: String?
)