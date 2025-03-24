package com.example.marketing.api

import com.example.marketing.dto.board.request.GetAdvertisementFreshResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import javax.inject.Inject

class AdvertisementApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun fetchFresh(): GetAdvertisementFreshResponse{
        return client.post("/test/advertisement/fresh").body()
    }
}