package com.example.marketing.dto.board.request

import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.TimelineDirection

data class GetDeliveryAdvertisementsTimelineByCategory(
    val pivotTime: Long,
    val timelineDirection: TimelineDirection,
    val deliveryCategories: List<DeliveryCategory>
) {
    companion object {
        fun of(
            pivotTime: Long,
            timelineDirection: TimelineDirection,
            deliveryCategories: List<DeliveryCategory>
        ): GetDeliveryAdvertisementsTimelineByCategory {
            return GetDeliveryAdvertisementsTimelineByCategory(
                pivotTime = pivotTime,
                timelineDirection,
                deliveryCategories
            )
        }
    }
}
