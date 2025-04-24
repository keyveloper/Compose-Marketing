package com.example.marketing.repository

import com.example.marketing.api.AdvertisementDeliveryApi
import com.example.marketing.domain.AdvertisementDelivery
import com.example.marketing.domain.AdvertisementGeneral
import com.example.marketing.dto.board.request.GetAllDeliveriesTimelineByCategory
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

    suspend fun fetchById(targetId: Long): AdvertisementDelivery {
        val response = advertisementDeliveryApi.fetchById(targetId)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.advertisement
        }
    }

    suspend fun fetchAllTimelineByCategoryAndDirection(
        requestModel: GetAllDeliveriesTimelineByCategory
    ): List<AdvertisementDelivery> {
        val response = advertisementDeliveryApi.fetchAllTimelineByCategoryAndDirection(
            requestModel
        )

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.advertisements
        }
    }
}