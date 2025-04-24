package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementDelivery

data class GetDeliveryAdvertisementsTimelineByCategoryResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val advertisements: List<AdvertisementDelivery>
)