package com.example.marketing.repository

import com.example.marketing.api.FavoriteApi
import com.example.marketing.dto.functions.request.FavoriteAdvertisement
import com.example.marketing.dto.functions.response.FavoriteAdResult
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteApi: FavoriteApi
) {
    suspend fun favorite(advertisementId: Long): FavoriteAdResult? {
        val response = favoriteApi.favorite(
            FavoriteAdvertisement.of(advertisementId = advertisementId)
        )

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.result
        }
    }
}