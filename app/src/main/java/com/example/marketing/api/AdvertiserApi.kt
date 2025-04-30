package com.example.marketing.api

import com.example.marketing.dto.user.request.LoginAdvertiser
import com.example.marketing.dto.user.request.LoginAdvertiserRequest
import com.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import com.example.marketing.dto.user.request.SignUpAdvertiser
import com.example.marketing.dto.user.response.AdvertiserProfileResponse
import com.example.marketing.dto.user.response.LoginAdvertiserResponse
import com.example.marketing.dto.user.response.MakeNewAdvertiserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AdvertiserApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun signUp(requestModel: SignUpAdvertiser): MakeNewAdvertiserResponse {
        val request = MakeNewAdvertiserRequest.of(requestModel)
        return client.post("/test/advertiser") {
            setBody(request)
        }.body()
    }

    suspend fun login(requestModel: LoginAdvertiser): LoginAdvertiserResponse {
        val request = LoginAdvertiserRequest.of(requestModel)
        return client.post("/entry/login-advertiser") {
            setBody(request)
        }.body()
    }

    suspend fun fetchProfile(advertiserId: Long): AdvertiserProfileResponse {
        return client.get("/test/advertiser/profile") {
            parameter("advertiserId", advertiserId)
        }.body()
    }
}