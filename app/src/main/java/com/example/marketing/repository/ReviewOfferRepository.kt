package com.example.marketing.repository

import com.example.marketing.api.ReviewOfferApi
import com.example.marketing.dto.functions.request.OfferReview
import com.example.marketing.dto.functions.response.OfferingInfluencerInfo
import javax.inject.Inject

class ReviewOfferRepository @Inject constructor(
    private val reviewOfferApi: ReviewOfferApi
) {
    suspend fun fetchOfferingInfluencerInfos(
        advertisementId: Long
    ): List<OfferingInfluencerInfo> {
        val response = reviewOfferApi.fetchOfferingInfluencerInfos(advertisementId)

        return if (response.frontErrorCode != 20000) {
            listOf()
        } else {
            response.infos
        }
    }

    suspend fun offer(requestModel: OfferReview): Long? {
        val response = reviewOfferApi.offer(requestModel)

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.createdEntityId
        }
    }
}