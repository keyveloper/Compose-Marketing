package com.example.marketing.dto.user.request

data class SaveInfluencerProfileImage(
    val originalFileName: String,
    val imageType: String
    ) {
    companion object {
        fun of(originalFileName: String, imageType: String): SaveInfluencerProfileImage =
            SaveInfluencerProfileImage(originalFileName, imageType)
    }
}
