package com.example.marketing.repository

import com.example.marketing.api.FollowApi
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.domain.FollowFeedItem
import com.example.marketing.dto.functions.request.FollowAdvertiser
import com.example.marketing.dto.functions.response.FollowAdvertiserResult
import javax.inject.Inject

class FollowRepository @Inject constructor(
    private val followApi: FollowApi
){
    suspend fun follow(requestModel: FollowAdvertiser): FollowAdvertiserResult? {
        val response = followApi.follow(requestModel)

        return if(response.frontErrorCode != 20000) {
            null
        } else {
            response.result
        }
    }

    suspend fun fetchAdsByInfluencerId(): List<FollowFeedItem> {
        val response = followApi.fetchAdsByInfluencerId()

        return if (response.frontErrorCode != 20000) {
            listOf()
        } else {
            response.packages.map {
                FollowFeedItem.of(
                    it.advertisementPackage.advertisementGeneralFields,
                    it.advertiserSummaryInfo
                )
            }
        }
    }
}