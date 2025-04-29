package com.example.marketing.domain

data class AdvertisementImage(
    val id: Long,
    val advertisementId: Long,
    val apiCallUri: String,
    val fileSizeKB: Long,
    val fileType: String,
    val isThumbnail: Boolean,
    val createdAt: Long,
)