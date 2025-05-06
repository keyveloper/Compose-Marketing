package com.example.marketing.dto.functions.request

data class OfferReview(
    val offer: String,
    val advertisementId: Long
) {
    companion object {
        fun of(
            offer: String,
            advertisementId: Long
        ): OfferReview = OfferReview(
            offer,
            advertisementId
        )
    }
}
