package com.example.marketing.dto.functions.response

data class GetOfferingInfluencerInfosResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val infos: List<OfferingInfluencerInfo>
)