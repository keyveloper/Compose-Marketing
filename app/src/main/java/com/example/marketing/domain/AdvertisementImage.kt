package com.example.marketing.domain

data class AdvertisementImage(
    val entityId: Long,
    val advertisementId: Long?,
    val apiCallUri: String,
    val createdAt: Long,
    val draftId: Long
)