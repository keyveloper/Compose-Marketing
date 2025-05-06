package com.example.marketing.dto.user.request

data class MakeNewInfluencerProfileInfoRequest(
    val introduction: String?,
    val job: String?
) {
    companion object {
        fun of(
            requestModel: SaveInfluencerProfileInfo
        ): MakeNewInfluencerProfileInfoRequest =
            MakeNewInfluencerProfileInfoRequest(
                requestModel.introduction,
                requestModel.job
            )
    }
}
