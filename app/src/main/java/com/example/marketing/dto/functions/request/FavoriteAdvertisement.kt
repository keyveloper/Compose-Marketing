package com.example.marketing.dto.functions.request

data class FavoriteAdvertisement(
    val advertisementId: Long
) {
    companion object {
        fun of(advertisementId: Long): FavoriteAdvertisement =
            FavoriteAdvertisement(advertisementId = advertisementId)
    }
}
