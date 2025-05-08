package com.example.marketing.domain

import com.example.marketing.enums.FavoriteStatus

data class InfluencerFavoriteAdWithThumbnail(
    val advertisementId: Long,
    val thumbnailUrl: String,
    val favoriteStatus: FavoriteStatus
)