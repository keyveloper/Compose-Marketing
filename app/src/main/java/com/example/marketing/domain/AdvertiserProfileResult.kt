package com.example.marketing.domain

data class AdvertiserProfileResult(
    val advertiserId: Long,
    val companyName: String,
    val companyInfo: String?,
    val companyLocation: String?,
    val introduction: String?,
    val followerCount: Long?,
)
