package com.example.marketing.dto.functions.request


data class NewOfferReviewRequest(
    val advertisementId: Long,
    val offer: String,
) {
    companion object {
        fun of(requestModel: OfferReview): NewOfferReviewRequest =
            NewOfferReviewRequest(
                advertisementId = requestModel.advertisementId,
                offer = requestModel.offer
            )
    }
}
