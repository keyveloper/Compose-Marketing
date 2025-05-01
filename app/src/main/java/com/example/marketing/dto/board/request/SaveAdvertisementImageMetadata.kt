package com.example.marketing.dto.board.request

data class SaveAdvertisementImageMetadata(
    val originalFileName: String,
    val imageType: String,
    val draftId: Long
) {
    companion object {
        fun of(
            originalFileName: String,
            imageType: String,
            draftId: Long
        ): SaveAdvertisementImageMetadata =
            SaveAdvertisementImageMetadata(
                originalFileName,
                imageType,
                draftId
            )
    }
}
