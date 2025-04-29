package com.example.marketing.dto.functions.response

import com.example.marketing.domain.AdvertisementPackage

data class GetFavoriteAdsResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val packages: List<AdvertisementPackage>
)