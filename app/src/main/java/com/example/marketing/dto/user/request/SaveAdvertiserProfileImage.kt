package com.example.marketing.dto.user.request

import com.example.marketing.enums.ProfileImageType

data class SaveAdvertiserProfileImage(
    val originalFileName: String,
    val profileImageType: ProfileImageType,
    val imageType: String,
) {
    companion object {
        fun of(
            originalFileName: String,
            profileImageType: ProfileImageType,
            imageType: String
        ): SaveAdvertiserProfileImage =
            SaveAdvertiserProfileImage(
                originalFileName,
                profileImageType,
                imageType
            )
    }
}