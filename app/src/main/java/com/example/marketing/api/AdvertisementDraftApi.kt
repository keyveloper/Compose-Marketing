package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.board.request.DeleteAdDraft
import com.example.marketing.dto.board.response.DeleteAdDraftResponse
import com.example.marketing.dto.board.response.IssueNewAdvertisementDraftResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.post
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AdvertisementDraftApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun fetchNewDraft(): IssueNewAdvertisementDraftResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/advertisement/new-draft") {
            bearerAuth(token)
        }.body()
    }

    suspend fun deleteDraftById(requestModel: DeleteAdDraft): DeleteAdDraftResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.delete("/advertisement/draft/${requestModel.entityId}") {
            bearerAuth(token)
        }.body()
    }
}

