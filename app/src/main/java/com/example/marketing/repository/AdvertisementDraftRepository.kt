package com.example.marketing.repository

import android.util.Log
import com.example.marketing.api.AdvertisementDraftApi
import com.example.marketing.domain.AdvertisementDraft
import javax.inject.Inject

class AdvertisementDraftRepository @Inject constructor(
    private val advertisementDraftApi: AdvertisementDraftApi
) {
    suspend fun issue(): AdvertisementDraft? {
        val response = advertisementDraftApi.fetchNewDraft()
        return if (response.frontErrorCode != 20000) {
            Log.i("adDraftRepo", "issue new draft failed : ${response.errorMessage}")
            null
        } else {
            response.advertisementDraft
        }
    }

    suspend fun deleteByEntityId(entityId: Long) {
        val response = advertisementDraftApi.fetchNewDraft()
        if (response.frontErrorCode != 20000) {
            Log.i("adDraftRepo", "deleteByEntityId(): ${response.errorMessage}")
        }
    }


}