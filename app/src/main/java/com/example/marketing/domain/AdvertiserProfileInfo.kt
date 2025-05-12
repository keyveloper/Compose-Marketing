package com.example.marketing.domain

data class AdvertiserProfileInfo(
    val advertiserId: Long,
    val advertiserLoginId: String,
    val companyName: String,
    val email: String,
    val contact: String,
    val homepageUrl: String?,
    val createdAt: Long,
    val profileUnifiedCode: String,
    val backgroundUnifiedCode: String,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
)