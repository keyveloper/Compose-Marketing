package com.example.marketing.dto.functions.request

data class FollowAdvertiser(
    val advertiserId: Long
) {
    companion object {
        fun of(
            advertiserId: Long
        ): FollowAdvertiser = FollowAdvertiser(advertiserId)
    }
}
