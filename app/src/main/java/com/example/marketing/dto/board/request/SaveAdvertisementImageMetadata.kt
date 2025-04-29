package com.example.marketing.dto.board.request

data class SaveAdvertisementImageMetadata(
    val advertisementId: Long,
    val originalFileName: String,
    val isThumbnail: Boolean,
    val imageType: String
)
