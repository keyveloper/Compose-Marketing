package com.example.marketing.dto.board.request

import android.net.Uri
import com.example.marketing.enums.DeliveryCategory

data class AdvertisementWithImageAndKeyword(
    val commonFields: AdvertisementCommonFields,
    val imageUris: List<Uri>,
    val keywords: Set<String>,
    val categories: List<DeliveryCategory>? //👉 for delivery
)
