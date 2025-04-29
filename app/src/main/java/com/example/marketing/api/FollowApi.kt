package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.functions.request.FollowAdvertiser
import com.example.marketing.dto.functions.request.FollowAdvertiserRequest
import com.example.marketing.dto.functions.response.FollowAdvertiserResponse
import com.example.marketing.dto.functions.response.GetFollowingAdvertiserInfosResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
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

            headers {
                append(HttpHeaders.Authorization, token)
            }
        }.body()
    }

    suspend fun fetchAdvertiserInfo(): GetFollowingAdvertiserInfosResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.get("/follow/following-advertiser-infos") {
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }.body()
    }
}