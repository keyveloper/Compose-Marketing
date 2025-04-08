package com.example.marketing.domain

import com.example.marketing.enums.ChannelType

data class Influencer(
    val loginId: String,
    val introduction: String,
    val profileImgUrl: String,
    val email: String,

    // channel
)
