package com.example.marketing.dto.user.request

data class LoginAdvertiser(
    val loginId: String,
    val password: String
) {
    companion object {
        fun of(
            loginId: String,
            password: String
        ): LoginAdvertiser {
            return LoginAdvertiser(
                loginId = loginId,
                password = password
            )
        }
    }
}