package com.example.marketing.api

import com.example.marketing.dto.board.request.GetAdvertisementFreshResponse
import com.example.marketing.dto.board.response.GetAdvertisementResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import javax.inject.Inject

class AdvertisementApi @Inject constructor(
    private val client: HttpClient
) {
    // basic api
    suspend fun fetchById(id : Long): GetAdvertisementResponse {
        return client.get("/test/advertisement/$id").body()
    }

    // timeline api
    suspend fun fetchFresh(): GetAdvertisementFreshResponse{
        return client.post("/test/advertisement/fresh").body()
    }


}