package com.example.marketing.dto.board.request

import com.example.marketing.enums.DeliveryCategory

data class MakeNewAdvertisementDeliveryRequest(
    val advertisementGeneralRequest: MakeNewAdvertisementGeneralRequest,
    val categories: List<DeliveryCategory>
) {
    companion object {
        fun of(
            requestModel: SaveAdvertisementDelivery,
        ): MakeNewAdvertisementDeliveryRequest {
            return MakeNewAdvertisementDeliveryRequest(
                advertisementGeneralRequest = MakeNewAdvertisementGeneralRequest
                    .of(requestModel.saveAdvertisementGeneral),
                categories = requestModel.categories
            )
        }
    }
}