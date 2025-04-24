package com.example.marketing.domain

import com.example.marketing.enums.AdvertisementType


data class AdvertisementGeneralForReturn(
    override val advertisementGeneral: AdvertisementGeneral,
    override val advertisementType: AdvertisementType = AdvertisementType.GENERAL
): Advertisement

