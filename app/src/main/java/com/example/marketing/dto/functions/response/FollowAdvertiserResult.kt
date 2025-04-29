package com.example.marketing.dto.functions.response

import com.example.marketing.enums.FollowStatus

data class FollowAdvertiserResult(
    val influencerId: Long,
    val advertiserId: Long,
    val followStatus: FollowStatus
)