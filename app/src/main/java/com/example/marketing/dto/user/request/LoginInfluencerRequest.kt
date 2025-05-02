package com.example.marketing.dto.user.request

data class LoginInfluencerRequest(
    val loginId: String,

    val password: String
) {
    companion object {
        fun of(requestModel: LoginInfluencer): LoginInfluencerRequest =
            LoginInfluencerRequest(requestModel.loginId, requestModel.password)
    }
}
