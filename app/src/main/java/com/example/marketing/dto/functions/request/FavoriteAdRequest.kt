package com.example.marketing.dto.functions.request

import com.example.marketing.dto.functions.response.FavoriteAdResponse

data class FavoriteAdRequest(
   val advertisementId: Long
) {
    companion object {
        fun of(requestModel: FavoriteAdvertisement): FavoriteAdRequest =
            FavoriteAdRequest(advertisementId = requestModel.advertisementId)
    }
}
