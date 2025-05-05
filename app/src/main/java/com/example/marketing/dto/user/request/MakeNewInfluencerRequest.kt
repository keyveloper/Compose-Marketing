package com.example.marketing.dto.user.request

data class MakeNewInfluencerRequest(
    val loginId: String,
    val password: String,
    val birthday: String,
    val blogUrl: String?,
    val instagramUrl: String?,
    val threadUrl: String?,
    val youtuberUrl: String?,
) {
    companion object {
        fun of(
            requestModel: SignUpInfluencer,
        ): MakeNewInfluencerRequest =
            MakeNewInfluencerRequest(
                requestModel.loginId,
                requestModel.password,
                requestModel.birthday,
                requestModel.blogUrl,
                requestModel.instagramUrl,
                requestModel.threadUrl,
                requestModel.youtuberUrl
            )
    }
}