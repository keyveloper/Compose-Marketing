package com.example.marketing.dto.board.request

import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimeLineDirection

data class GetAllDeliveriesTimelineByCategory(
    val pivotTime: Long,
    val timeLineDirection: TimeLineDirection,
    val deliveryCategory: DeliveryCategory
) {
    companion object {
        fun of(
            pivotTime: Long,
            timeLineDirection: TimeLineDirection,
            deliveryCategory: DeliveryCategory
        ): GetAllDeliveriesTimelineByCategory {
            return GetAllDeliveriesTimelineByCategory(
                pivotTime = pivotTime,
                timeLineDirection,
                deliveryCategory
            )
        }
    }
}
