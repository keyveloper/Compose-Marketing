package com.example.marketing.api

import com.example.marketing.domain.Admin
import com.example.marketing.dto.user.request.MakeNewAdminRequest
import com.example.marketing.dto.user.request.SignUpAdmin
import com.example.marketing.dto.user.response.MakeNewAdminResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AdminApi @Inject constructor(
    private val client: HttpClient
) { 
    suspend fun signUp(requestModel: SignUpAdmin): MakeNewAdminResponse {
        val request = MakeNewAdminRequest.of(requestModel)
        return client.post("/test/admin") {
            setBody(request)
        }.body()
    }
}