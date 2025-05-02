package com.example.marketing.dto.user.request

data class LoginInfluencer(
    val loginId: String,
    val password: String,
) {
    companion object {
        fun of(loginId: String, password: String): LoginInfluencer
        = LoginInfluencer(loginId, password)
    }
}
