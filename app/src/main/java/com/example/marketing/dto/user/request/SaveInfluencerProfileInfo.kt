package com.example.marketing.dto.user.request

data class SaveInfluencerProfileInfo(
    val introduction: String?,
    val job: String?
) {
    companion object {
        fun of(
            introduction: String?,
            job: String?
        ): SaveInfluencerProfileInfo = SaveInfluencerProfileInfo(
            introduction,
            job
        )
    }
}