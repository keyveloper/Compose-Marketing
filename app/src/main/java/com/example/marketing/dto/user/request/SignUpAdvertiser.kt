package com.example.marketing.dto.user.request

import com.example.marketing.enums.AdvertiserType

data class SignUpAdvertiser(
    val loginId: String,
    val password: String,
    val email: String = "d@1",

    val contact: String = "1111",

    var homepageUrl: String?,

    var companyName: String,

    val advertiserType: AdvertiserType,
)
