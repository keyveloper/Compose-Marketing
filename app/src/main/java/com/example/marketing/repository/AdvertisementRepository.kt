package com.example.marketing.repository

import android.util.Log
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

    suspend fun fetchFresh(): List<Advertisement> {
        val response = advertisementApi.fetchFresh()

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            Log.i("advertisement Repository", "return advertisement:" +
                    "${response.advertisements}")
            response.advertisements
        }
    }
}