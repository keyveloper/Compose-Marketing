package com.example.marketing.dto.user.request

import com.example.marketing.enums.ProfileImageType
import kotlinx.serialization.Serializable

@Serializable
data class MakeNewAdvertiserProfileImageRequest(
    val originalFileName: String,
    val profileImageType: ProfileImageType
) {
    companion object {
        fun of(requestModel: SaveAdvertiserProfileImage): MakeNewAdvertiserProfileImageRequest =
            MakeNewAdvertiserProfileImageRequest(
                originalFileName = requestModel.originalFileName,
                profileImageType = requestModel.profileImageType
            )
    }
}