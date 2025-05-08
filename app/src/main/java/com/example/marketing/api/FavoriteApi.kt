package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.functions.request.FavoriteAdRequest
import com.example.marketing.dto.functions.request.FavoriteAdvertisement
import com.example.marketing.dto.functions.response.FavoriteAdResponse
import com.example.marketing.dto.functions.response.GetInfluencerPersonalFavoriteAdsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FavoriteApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun favorite(requestModel: FavoriteAdvertisement): FavoriteAdResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/favorite") {
            setBody(
                FavoriteAdRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun fetchAllAdsWithThumbnail(): GetInfluencerPersonalFavoriteAdsResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.get("/favorite/ads/influencer-personal") {
            bearerAuth(token)
        }.body()
    }
}

