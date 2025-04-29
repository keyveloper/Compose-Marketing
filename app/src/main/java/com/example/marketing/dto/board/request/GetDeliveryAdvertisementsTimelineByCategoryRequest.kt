package com.example.marketing.dto.board.request

import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimelineDirection

data class GetDeliveryAdvertisementsTimelineByCategoryRequest(
    val pivotTime: Long,
    val timelineDirection: TimelineDirection,
    val deliveryCategory: DeliveryCategory
) {
    companion object {
        fun of(
            requestModel: GetAllDeliveriesTimelineByCategory
        ): GetDeliveryAdvertisementsTimelineByCategoryRequest {
            return GetDeliveryAdvertisementsTimelineByCategoryRequest(
                pivotTime = requestModel.pivotTime,
                timelineDirection = requestModel.timelineDirection,
                deliveryCategory = requestModel.deliveryCategory
            )
        }
    }
}

