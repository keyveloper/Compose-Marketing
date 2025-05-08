package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.functions.request.NewOfferReviewRequest
import com.example.marketing.dto.functions.request.OfferReview
import com.example.marketing.dto.functions.response.GetOfferingInfluencerInfosResponse
import com.example.marketing.dto.functions.response.GetValidReviewOfferAdResponse
import com.example.marketing.dto.functions.response.NewOfferReviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ReviewOfferApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun offer(requestModel: OfferReview): NewOfferReviewResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/review-offer") {
            setBody(
                NewOfferReviewRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun fetchOfferingInfluencerInfos(
        advertisementId: Long
    ): GetOfferingInfluencerInfosResponse {
        return httpClient.get("/open/review-offers/$advertisementId")
            .body()
    }

    suspend fun fetchAllValidOfferByInfluencerId(): GetValidReviewOfferAdResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.get("/review-offers/influencer-valid") {
            bearerAuth(token)
        }.body()
    }
}