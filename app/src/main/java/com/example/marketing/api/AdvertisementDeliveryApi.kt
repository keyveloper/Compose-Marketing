package com.example.marketing.api

import com.example.marketing.dto.board.request.GetAllDeliveriesTimelineByCategory
import com.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategoryRequest
import com.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import com.example.marketing.dto.board.request.SaveAdvertisementDelivery
import com.example.marketing.dto.board.request.SaveAdvertisementGeneral
import com.example.marketing.dto.board.response.GetDeliveryAdvertisementsTimelineByCategoryResponse
import com.example.marketing.dto.board.response.MakeNewAdvertisementDeliveryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AdvertisementDeliveryApi @Inject constructor(
    private val httpClient: HttpClient
){
    suspend fun save(
        requestModel: SaveAdvertisementDelivery
    ): MakeNewAdvertisementDeliveryResponse {
        return httpClient.post("/test/advertisement/delivery") {
            setBody(
                MakeNewAdvertisementDeliveryRequest.of(requestModel)
            )
        }.body()
    }

    suspend fun fetchAllTimelineByCategoryAndDirection(
        requestModel: GetAllDeliveriesTimelineByCategory
    ): GetDeliveryAdvertisementsTimelineByCategoryResponse {
        return httpClient.post ("/test/advertisement/deliveries-timeline/by-category") {
            setBody(GetDeliveryAdvertisementsTimelineByCategoryRequest.of(requestModel))
        }.body()
    }
}
