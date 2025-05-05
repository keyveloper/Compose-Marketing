package com.example.marketing.dto.user.request

import com.example.marketing.enums.AdvertiserType

data class SignUpAdvertiser(
    val loginId: String,
    val password: String,
    val email: String,
    val contact: String,
    var homepageUrl: String?,
    var companyName: String,
    val advertiserType: AdvertiserType,
)
