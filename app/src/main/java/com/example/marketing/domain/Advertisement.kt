package com.example.marketing.domain

import com.example.marketing.enums.AdvertisementType


interface Advertisement {
    val advertisementGeneral: AdvertisementGeneral
    val advertisementType: AdvertisementType
}