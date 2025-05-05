package com.example.marketing.repository

import android.util.Log
import com.example.marketing.api.AdvertisementEventApi
import com.example.marketing.domain.AdvertisementPackage
import javax.inject.Inject

class AdvertisementEventRepository @Inject constructor(
    private val advertisementEventApi: AdvertisementEventApi
) {
    // -------------- 🌃 EVENT -------------------
    suspend fun testFetch(): AdvertisementPackage? {
        val response = advertisementEventApi.testFetch()

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.advertisement
        }
    }

    suspend fun fetchFresh(): List<AdvertisementPackage> {
        val response = advertisementEventApi.fetchFresh()

        return if (response.frontErrorCode != 20000) {
            Log.i("adEventRepo", "fresh: response is not Okay" +
                    "${response.packages}")
            listOf()
        } else {
            response.packages
        }
    }

    suspend fun fetchDeadline(): List<AdvertisementPackage> {
        val response = advertisementEventApi.fetchDeadline()

        return if (response.frontErrorCode != 20000) {
            Log.i("adEventRepo", "deadline: response is not Okay" +
                    "${response.packages}")
            listOf()
        } else {
            response.packages
        }
    }
}