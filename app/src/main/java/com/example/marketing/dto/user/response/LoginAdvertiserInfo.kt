package com.example.marketing.dto.user.response

data class LoginAdvertiserInfo(
    val jwt: String,
    val id: Long
) {
    companion object {
        fun of(
            jwt: String,
            id: Long
        ): LoginAdvertiserInfo {
            return LoginAdvertiserInfo(
                jwt,
                id
            )
        }
    }
}
