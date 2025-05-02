package com.example.marketing.api

import com.example.marketing.dto.user.request.LoginInfluencer
import com.example.marketing.dto.user.request.LoginInfluencerRequest
import com.example.marketing.dto.user.response.LoginInfluencerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class InfluencerApi @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun login(requestModel: LoginInfluencer): LoginInfluencerResponse {
        return httpClient.post("/entry/login-influencer") {
            setBody(
                LoginInfluencerRequest.of(requestModel)
            )
        }.body()
    }
}