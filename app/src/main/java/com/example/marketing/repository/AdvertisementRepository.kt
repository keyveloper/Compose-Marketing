package com.example.marketing.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.marketing.api.AdvertisementApi
import com.example.marketing.api.AdvertisementDraftApi
import com.example.marketing.api.AdvertisementImageApi
import com.example.marketing.domain.Advertisement
import com.example.marketing.dto.board.request.AdvertisementWithKeyword
import com.example.marketing.dto.board.request.SaveAdvertisementDelivery
import com.example.marketing.dto.board.request.SaveAdvertisementGeneral
import com.example.marketing.dto.board.request.SaveAdvertisementImageMetadata
import com.example.marketing.dto.board.response.MakeNewAdvertisementImageResult
import com.example.marketing.enums.ReviewType
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class AdvertisementRepository @Inject constructor(
    private val advertisementApi: AdvertisementApi,
    private val advertisementImageApi: AdvertisementImageApi,
    private val advertisementDraftApi: AdvertisementDraftApi
    // keyword Api added
) {
    suspend fun upload(businessModel: AdvertisementWithKeyword): Long {
        val requestModel = SaveAdvertisementGeneral.of(
            title = businessModel.commonFields.title,
            reviewType = businessModel.commonFields.reviewType,
            channelType = businessModel.commonFields.channelType,
            itemName = businessModel.commonFields.itemName,
            itemInfo = businessModel.commonFields.itemInfo,
            recruitmentNumber = businessModel.commonFields.recruitmentNumber,
            recruitmentStartAt = businessModel.commonFields.recruitmentStartAt,
            recruitmentEndAt = businessModel.commonFields.recruitmentEndAt,
            announcementAt = businessModel.commonFields.announcementAt,
            reviewStartAt = businessModel.commonFields.reviewStartAt,
            reviewEndAt = businessModel.commonFields.reviewEndAt,
            endAt = businessModel.commonFields.endAt,
            siteUrl = businessModel.commonFields.siteUrl
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