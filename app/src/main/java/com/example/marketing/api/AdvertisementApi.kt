package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import com.example.marketing.dto.board.request.MakeNewAdvertisementGeneralRequest
import com.example.marketing.dto.board.request.SaveAdvertisementDelivery
import com.example.marketing.dto.board.request.SaveAdvertisementGeneral
import com.example.marketing.dto.board.response.GetAdvertisementFreshResponse
import com.example.marketing.dto.board.response.GetAdvertisementGeneralResponse
import com.example.marketing.dto.board.response.MakeNewAdvertisementDeliveryResponse
import com.example.marketing.dto.board.response.MakeNewAdvertisementGeneralResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AdvertisementApi @Inject constructor(
    private val client: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun uploadGeneral(requestModel: SaveAdvertisementGeneral):
            MakeNewAdvertisementGeneralResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return client.post("/advertisement/general") {
            setBody(
                MakeNewAdvertisementGeneralRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun uploadDelivery(requestModel: SaveAdvertisementDelivery):
            MakeNewAdvertisementDeliveryResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return client.post("/advertisement/delivery") {
            setBody(
                MakeNewAdvertisementDeliveryRequest.of(requestModel)
            )

            bearerAuth(token)
        }.body()
    }

    suspend fun fetchById(targetId: Long): GetAdvertisementGeneralResponse {
        return client.get("/open/advertisement/general/$targetId").body()
    }
}