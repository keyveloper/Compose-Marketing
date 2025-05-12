package com.example.marketing.dto.user.request

data class MakeNewAdvertiserProfileInfoRequest(
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
) {
    companion object {
        fun of(requestModel :SaveAdvertiserProfileInfo): MakeNewAdvertiserProfileInfoRequest =
            MakeNewAdvertiserProfileInfoRequest(
                serviceInfo = requestModel.serviceInfo,
                locationBrief = requestModel.locationBrief,
                introduction = requestModel.introduction
            )
    }
}