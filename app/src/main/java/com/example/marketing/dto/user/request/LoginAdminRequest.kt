package com.example.marketing.dto.user.request

data class LoginAdminRequest(
    val loginId: String,
    val password: String
) {
    companion object {
        fun of(
            requestModel: LoginAdmin
        ): LoginAdminRequest {
            return LoginAdminRequest(
                loginId = requestModel.loginId,
                password = requestModel.password
            )
        }
    }
}
