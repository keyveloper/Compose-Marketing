package com.example.marketing.dto.board.request

import kotlinx.serialization.Serializable

@Serializable
data class MakeNewAdvertisementImageRequest(
    val originalFileName: String,
    val draftId: Long
) {
    companion object {
        fun of(requestModel: SaveAdvertisementImageMetadata): MakeNewAdvertisementImageRequest =
            MakeNewAdvertisementImageRequest(
                originalFileName = requestModel.originalFileName,
                draftId = requestModel.draftId
            )
    }
}