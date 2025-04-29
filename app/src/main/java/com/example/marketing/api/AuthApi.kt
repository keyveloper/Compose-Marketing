package com.example.marketing.api

import com.example.marketing.dto.ValidateTokenRequest
import com.example.marketing.dto.ValidateTokenResponse
import com.example.marketing.dto.user.request.LoginAdvertiser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient
) {
    // token - valid test ...
    suspend fun validateToken(token: String): ValidateTokenResponse {
        return client.post("/validate-token") {
            setBody(
                ValidateTokenRequest.of(token)
            )
        }.body()
    }
}