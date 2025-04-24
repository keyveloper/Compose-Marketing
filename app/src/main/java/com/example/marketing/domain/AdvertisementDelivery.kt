package com.example.marketing.domain

import com.example.marketing.enums.AdvertisementType
import com.example.marketing.enums.DeliveryCategory

data class AdvertisementDelivery(
    override val advertisementGeneral: AdvertisementGeneral,
    override val advertisementType: AdvertisementType = AdvertisementType.DELIVERY,
    val categories: List<DeliveryCategory>
): Advertisement
