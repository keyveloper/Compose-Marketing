package com.example.marketing.dto.user.request

import kotlinx.serialization.Serializable

@Serializable
data class MakeNewInfluencerProfileImageRequest(
    val originalFileName: String,
) {
    companion object {
        fun of(requestModel: SaveInfluencerProfileImage): MakeNewInfluencerProfileImageRequest
        = MakeNewInfluencerProfileImageRequest(
            requestModel.originalFileName
        )
    }
}
