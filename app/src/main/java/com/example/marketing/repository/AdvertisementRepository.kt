package com.example.marketing.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.marketing.api.AdvertisementApi
import com.example.marketing.api.AdvertisementDraftApi
import com.example.marketing.api.AdvertisementImageApi
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.dto.board.request.AdvertisementWithKeyword
import com.example.marketing.dto.board.request.SaveAdvertisementDelivery
import com.example.marketing.dto.board.request.SaveAdvertisementGeneral
import com.example.marketing.dto.board.request.SaveAdvertisementImageMetadata
import com.example.marketing.dto.board.response.MakeNewAdvertisementImageResult
import com.example.marketing.enums.ReviewType
import com.example.marketing.exception.BusinessException
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import javax.inject.Inject

class AdvertisementRepository @Inject constructor(
    private val advertisementApi: AdvertisementApi,
    private val advertisementImageApi: AdvertisementImageApi,
    private val advertisementDraftApi: AdvertisementDraftApi
    // keyword Api added
) {
    suspend fun upload(businessModel: AdvertisementWithKeyword): Long {
        val requestModel = SaveAdvertisementGeneral.of(
            businessModel.commonFields
        )

        val createdAdvertisementId = when (businessModel.commonFields.reviewType) {
            ReviewType.DELIVERY -> {
                advertisementApi.uploadDelivery(
                    SaveAdvertisementDelivery.of(
                        requestModel,
                        businessModel.categories ?: listOf()
                    )
                ).createdId
            }

            else -> {
                advertisementApi.uploadGeneral(requestModel).createdId
            }
        }

        return createdAdvertisementId
    }

    suspend fun fetchById(targetId: Long): AdvertisementPackage? {
        val response = advertisementApi.fetchById(targetId)
        return if (response.frontErrorCode != 20000) {
            Log.e("adRepo", "fetchById(): response error")
            null
        } else response.advertisement
    }

}