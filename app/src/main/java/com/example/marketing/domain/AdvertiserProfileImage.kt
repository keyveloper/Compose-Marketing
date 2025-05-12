package com.example.marketing.domain

import com.example.marketing.enums.ProfileImageType

data class AdvertiserProfileImage(
    val metaEntityId: Long,
    val unifiedCode: String,
    val profileImageType: ProfileImageType
)