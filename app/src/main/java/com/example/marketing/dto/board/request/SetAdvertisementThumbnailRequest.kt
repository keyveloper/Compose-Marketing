package com.example.marketing.dto.board.request

data class SetAdvertisementThumbnailRequest(
    val entityId: Long,
    val advertisementId: Long
) {
    companion object {
        fun of(
            requestModel: SetAdvertisementThumbnail
        ): SetAdvertisementThumbnailRequest =
            SetAdvertisementThumbnailRequest(
                entityId = requestModel.entityId,
                advertisementId = requestModel.advertisementId
            )
    }
}
