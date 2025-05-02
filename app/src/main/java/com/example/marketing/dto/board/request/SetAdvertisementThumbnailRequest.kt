package com.example.marketing.dto.board.request

data class SetAdvertisementThumbnailRequest(
    val imageId: Long,
    val advertisementId: Long
) {
    companion object {
        fun of(
            requestModel: SetAdvertisementThumbnail
        ): SetAdvertisementThumbnailRequest =
            SetAdvertisementThumbnailRequest(
                imageId = requestModel.imageId,
                advertisementId = requestModel.advertisementId
            )
    }
}
