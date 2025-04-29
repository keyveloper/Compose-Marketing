package com.example.marketing.dto.board.request

import androidx.compose.ui.graphics.vector.addPathNodes

data class MakeNewAdvertisementImageRequest(
    val advertisementId: Long,
    val originalFileName: String,
    val isThumbnail: Boolean
) {
    companion object {
        fun of(requestModel: SaveAdvertisementImageMetadata): MakeNewAdvertisementImageRequest =
            MakeNewAdvertisementImageRequest(
                advertisementId = requestModel.advertisementId,
                originalFileName = requestModel.originalFileName,
                isThumbnail = requestModel.isThumbnail
            )
    }
}