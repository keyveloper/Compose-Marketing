package com.example.marketing.dto.board.request

data class SetAdvertisementThumbnail(
    val imageId: Long,
    val advertisementId: Long
) {
    companion object {
        fun of(
            imageId: Long,
            advertisementId: Long
        ): SetAdvertisementThumbnail = SetAdvertisementThumbnail(imageId, advertisementId)
    }
}
