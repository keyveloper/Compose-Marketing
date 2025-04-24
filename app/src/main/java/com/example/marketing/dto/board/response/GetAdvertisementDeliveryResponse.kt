package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementDelivery


data class GetAdvertisementDeliveryResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val advertisement: AdvertisementDelivery
)