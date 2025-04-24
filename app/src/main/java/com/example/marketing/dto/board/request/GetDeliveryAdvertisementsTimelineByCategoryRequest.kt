package com.example.marketing.dto.board.request

import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimeLineDirection

data class GetDeliveryAdvertisementsTimelineByCategoryRequest(
    val pivotTime: Long,
    val timeLineDirection: TimeLineDirection,
    val deliveryCategory: DeliveryCategory
) {
    companion object {
        fun of(
            requestModel: GetAllDeliveriesTimelineByCategory
        ): GetDeliveryAdvertisementsTimelineByCategoryRequest {
            return GetDeliveryAdvertisementsTimelineByCategoryRequest(
                pivotTime = requestModel.pivotTime,
                timeLineDirection = requestModel.timeLineDirection,
                deliveryCategory = requestModel.deliveryCategory
            )
        }
    }
}

