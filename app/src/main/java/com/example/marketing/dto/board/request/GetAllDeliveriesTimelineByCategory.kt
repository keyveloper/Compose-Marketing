package com.example.marketing.dto.board.request

import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimelineDirection

data class GetAllDeliveriesTimelineByCategory(
    val pivotTime: Long,
    val timelineDirection: TimelineDirection,
    val deliveryCategory: DeliveryCategory
) {
    companion object {
        fun of(
            pivotTime: Long,
            timelineDirection: TimelineDirection,
            deliveryCategory: DeliveryCategory
        ): GetAllDeliveriesTimelineByCategory {
            return GetAllDeliveriesTimelineByCategory(
                pivotTime = pivotTime,
                timelineDirection,
                deliveryCategory
            )
        }
    }
}
