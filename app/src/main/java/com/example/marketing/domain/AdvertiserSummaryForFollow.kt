package com.example.marketing.domain

import com.example.marketing.enums.AdvertiserType

data class AdvertiserSummaryForFollow(
    val companyName: String,
    val advertiserType: AdvertiserType,
    val advertiserProfileUnifiedCode: String?
)
