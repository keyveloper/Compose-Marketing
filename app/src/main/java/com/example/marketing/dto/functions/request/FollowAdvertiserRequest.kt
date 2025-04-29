package com.example.marketing.dto.functions.request

data class FollowAdvertiserRequest(
    val advertiserId: Long
) {
    companion object {
        fun of(
            advertiserId: Long
        ): FollowAdvertiserRequest = FollowAdvertiserRequest(advertiserId)
    }
}
