package com.example.marketing.dto.board.request

import android.net.Uri
import com.example.marketing.enums.DeliveryCategory

data class AdvertisementWithKeyword(
    val commonFields: AdvertisementCommonFields,
    val keywords: Set<String>,
    val categories: List<DeliveryCategory>? //👉 for delivery
)
