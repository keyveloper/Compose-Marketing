package com.example.marketing.repository

import com.example.marketing.api.AdvertisementDeliveryApi
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategory
import com.example.marketing.dto.board.request.SaveAdvertisementDelivery
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class AdvertisementDeliveryRepository @Inject constructor(
    private val advertisementDeliveryApi: AdvertisementDeliveryApi
) {
    suspend fun save(requestModel: SaveAdvertisementDelivery): Long {
        val response = advertisementDeliveryApi.save(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.createdId
        }
    }


    suspend fun fetchAllTimelineByCategoryAndDirection(
        requestModel: GetDeliveryAdvertisementsTimelineByCategory
    ): List<AdvertisementPackage> {
        val response = advertisementDeliveryApi.fetchAllTimelineByCategoryAndDirection(
            requestModel
        )

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.packages
        }
    }
}