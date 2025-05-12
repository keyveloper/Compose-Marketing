package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.keyword.request.DigKeywordCandidates
import com.example.marketing.dto.keyword.request.DigKeywordCandidatesRequest
import com.example.marketing.dto.keyword.request.GenerateSeoOptimizedTitles
import com.example.marketing.dto.keyword.request.GenerateSeoOptimizedTitlesRequest
import com.example.marketing.dto.keyword.request.GetScrappedTopBlogVisitStatRequest
import com.example.marketing.dto.keyword.response.DigKeywordCandidatesResponse
import com.example.marketing.dto.keyword.response.GenerateSeoOptimizedTitlesResponse
import com.example.marketing.dto.keyword.response.GetScrappedTopBlogVisitStatResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GoldenKeywordApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun fetchDugCandidates(
        requestModel: DigKeywordCandidates
    ): DigKeywordCandidatesResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return httpClient.post("/test/golden/candidates") {
            setBody(
                DigKeywordCandidatesRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun fetchScrappedTopBlogStat(keyword: String): GetScrappedTopBlogVisitStatResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return httpClient.post("/test/golden/top-blogger") {
            setBody(
                GetScrappedTopBlogVisitStatRequest.of(keyword)
            )
            bearerAuth(token)
        }.body()
    }

    suspend fun generateSeoOptimizedTitles(
        requestModel: GenerateSeoOptimizedTitles
    ): GenerateSeoOptimizedTitlesResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return httpClient.post("/test/golden/titles") {
            setBody(
                GenerateSeoOptimizedTitlesRequest.of(requestModel)
            )
            bearerAuth(token)
        }.body()
    }

}

