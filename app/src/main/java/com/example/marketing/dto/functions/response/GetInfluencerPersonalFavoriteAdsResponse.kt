package com.example.marketing.dto.functions.response

import com.example.marketing.domain.InfluencerFavoriteAdWithThumbnail

data class GetInfluencerPersonalFavoriteAdsResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val result: List<InfluencerFavoriteAdWithThumbnail>
)