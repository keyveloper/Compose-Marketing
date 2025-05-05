package com.example.marketing.domain

import com.example.marketing.enums.DeliveryCategory

// 🙄 rebuild needed!!! - too much duplicated
data class AdvertisementPackage(
    val advertisementGeneralFields: AdvertisementGeneralFields,
    val categories: List<DeliveryCategory?>,
    // -> 📌 add location fields -> dti로 만들 것
)
