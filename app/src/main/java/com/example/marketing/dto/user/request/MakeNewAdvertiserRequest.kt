package com.example.marketing.dto.user.request

import com.example.marketing.enums.AdvertiserType

data class MakeNewAdvertiserRequest(
    val loginId: String,

    val password: String,

    val email: String,

    val contact: String,

    var homepageUrl: String?,

    var companyName: String,

    val advertiserType: AdvertiserType,
) {
    companion object {
        fun of(
            requestModel: SignUpAdvertiser
        ): MakeNewAdvertiserRequest {
            return MakeNewAdvertiserRequest(
                loginId = requestModel.loginId,
                password = requestModel.password,
                email = requestModel.email,
                contact = requestModel.contact,
                homepageUrl = requestModel.homepageUrl,
                companyName = requestModel.companyName,
                advertiserType = requestModel.advertiserType
            )
        }
    }
}