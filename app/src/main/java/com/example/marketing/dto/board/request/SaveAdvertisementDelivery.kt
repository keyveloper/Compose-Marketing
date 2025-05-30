package com.example.marketing.dto.board.request

import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.ReviewType

data class SaveAdvertisementDelivery(
    val saveAdvertisementGeneral: SaveAdvertisementGeneral,

    val categories: List<DeliveryCategory>,
) {
    companion object {
        fun of(
            general: SaveAdvertisementGeneral,
            categories: List<DeliveryCategory>
        ): SaveAdvertisementDelivery =
            SaveAdvertisementDelivery(
                saveAdvertisementGeneral = general,
                categories = categories
            )
    }
}