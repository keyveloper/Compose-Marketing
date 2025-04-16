package com.example.marketing.dto.user.request

data class LoginAdvertiserRequest(
    val loginId: String,

    val password: String
) {
    companion object {
        fun of(
            requestModel: LoginAdvertiser
        ): LoginAdvertiserRequest {
            return LoginAdvertiserRequest(
                loginId = requestModel.loginId,
                password = requestModel.password
            )
        }
    }
}