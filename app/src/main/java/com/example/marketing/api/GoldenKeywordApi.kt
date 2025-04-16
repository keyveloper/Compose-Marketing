package com.example.marketing.api

import com.example.marketing.dto.keyword.FetchGoldenKeywords
import com.example.marketing.dto.keyword.GetGoldenKeywordsRequest
import com.example.marketing.dto.keyword.GetGoldenKeywordsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import javax.inject.Inject

class GoldenKeywordApi @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun fetchGoldenKeywords(requestModel: FetchGoldenKeywords): GetGoldenKeywordsResponse {
        val request = GetGoldenKeywordsRequest.of(requestModel)
        return httpClient.get("/test/keywords/golden") {
            setBody(request)
        }.body()
    }
}