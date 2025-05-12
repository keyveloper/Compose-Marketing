package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.board.response.GetOwnedExpiredAdvertisementsResponse
import com.example.marketing.dto.board.response.GetOwnedLiveAdvertisementsResponse
import com.example.marketing.dto.user.request.MakeNewAdvertiserProfileInfoRequest
import com.example.marketing.dto.user.request.SaveAdvertiserProfileInfo
import com.example.marketing.dto.user.response.GetAdvertiserProfileResponse
import com.example.marketing.dto.user.response.MakeNewAdvertiserProfileInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AdvertiserProfileApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
){
    suspend fun fetchProfileInfo(advertiserId: Long): GetAdvertiserProfileResponse {
        return httpClient.get("/open/advertiser/profile/info/$advertiserId") {
        }.body()
    }

    suspend fun uploadProfileInfo(
        requestModel: SaveAdvertiserProfileInfo
    ): MakeNewAdvertiserProfileInfoResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return httpClient.post("/advertiser/profile/info") {
            setBody(
                MakeNewAdvertiserProfileInfoRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun fetchLiveAdvertisements(
        advertiserId: Long
    ): GetOwnedLiveAdvertisementsResponse {
        return httpClient.get("/open/advertiser/profile/live-ads/$advertiserId").body()
    }

    suspend fun fetchExpiredAdvertisements(
        advertiserId: Long
    ): GetOwnedExpiredAdvertisementsResponse {
        return httpClient.get("/open/advertiser/profile/expired-ads/$advertiserId").body()
    }
}

