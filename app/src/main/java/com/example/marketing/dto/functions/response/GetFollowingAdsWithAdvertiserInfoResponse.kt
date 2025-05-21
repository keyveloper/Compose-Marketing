package com.example.marketing.dto.functions.response

import com.example.marketing.domain.AdvertisementPackageWithAdvertiserSummaryInfo

data class GetFollowingAdsWithAdvertiserInfoResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val packages: List<AdvertisementPackageWithAdvertiserSummaryInfo>
)

