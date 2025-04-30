package com.example.marketing.api

import android.util.Log
import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.ValidateTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    // token - valid test ...
    suspend fun validateToken(): ValidateTokenResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        Log.i("authApi", "token: $token")
        return client.post("/validate-token") {
            bearerAuth(token)
        }.body()
    }
}