package com.example.marketing.api

import com.example.marketing.dto.board.response.GetAdvertisementDeadlineResponse
import com.example.marketing.dto.board.response.GetAdvertisementFreshResponse
import com.example.marketing.dto.board.response.GetAdvertisementGeneralResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class AdvertisementEventApi @Inject constructor(
    private val httpClient: HttpClient
){
    suspend fun testFetch(): GetAdvertisementGeneralResponse {
        return httpClient.get("/open/advertisement/general/5").body()
    }

    suspend fun fetchFresh(): GetAdvertisementFreshResponse {
        return httpClient.get("/open/advertisements/fresh").body()
    }

    suspend fun fetchDeadline(): GetAdvertisementDeadlineResponse {
        return httpClient.get("/open/advertisements/fresh").body()
    }
}