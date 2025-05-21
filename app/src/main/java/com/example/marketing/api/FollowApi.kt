package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.functions.request.FollowAdvertiser
import com.example.marketing.dto.functions.request.FollowAdvertiserRequest
import com.example.marketing.dto.functions.response.FollowAdvertiserResponse
import com.example.marketing.dto.functions.response.GetFollowingAdsWithAdvertiserInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FollowApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
){

    suspend fun follow(requestModel: FollowAdvertiser): FollowAdvertiserResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/follow") {
            setBody(
                FollowAdvertiserRequest.of(requestModel.advertiserId)
            )

            bearerAuth(token)

        }.body()
    }

    suspend fun fetchAdsByInfluencerId(): GetFollowingAdsWithAdvertiserInfoResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.get("/follow/advertisements/advertisers") {
            bearerAuth(token)
        }.body()
    }
}