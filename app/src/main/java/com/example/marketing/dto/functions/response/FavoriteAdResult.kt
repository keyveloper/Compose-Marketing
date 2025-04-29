package com.example.marketing.dto.functions.response

import com.example.marketing.enums.FavoriteStatus

data class FavoriteAdResult(
    val favoriteEntityId: Long,
    val favoriteStatus: FavoriteStatus
)