package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.user.request.MakeNewInfluencerProfileInfoRequest
import com.example.marketing.dto.user.request.SaveInfluencerProfileInfo
import com.example.marketing.dto.user.response.GetInfluencerProfileResponse
import com.example.marketing.dto.user.response.MakeNewInfluencerProfileInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class InfluencerProfileApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {

    suspend fun uploadProfileInfo(
        requestModel: SaveInfluencerProfileInfo
    ): MakeNewInfluencerProfileInfoResponse {
        return httpClient.post("/influencer/profile/info") {
            val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
            setBody(
                MakeNewInfluencerProfileInfoRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun fetchProfileInfo(influencerId: Long): GetInfluencerProfileResponse {
        return httpClient.get("/open/influencer/profile/info/$influencerId").body()
    }

}

