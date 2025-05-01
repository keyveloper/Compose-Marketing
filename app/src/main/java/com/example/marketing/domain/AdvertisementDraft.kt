package com.example.marketing.domain

import com.example.marketing.enums.DraftStatus

data class AdvertisementDraft(
    val id: Long,
    val advertiserId: Long,
    val draftStatus: DraftStatus,
    val expiredAt: Long,
    val createdAt: Long,
    val updatedAt: Long
)