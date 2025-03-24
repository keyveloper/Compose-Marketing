package com.example.marketing.repository

import com.example.marketing.api.AdvertisementApi
import com.example.marketing.domain.Advertisement
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class AdvertisementRepository @Inject constructor(
    private val advertisementApi: AdvertisementApi
) {

    suspend fun fetchById(id: Long): Advertisement {
        val response = advertisementApi.fetchById(id)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.advertisement
        }
    }
}