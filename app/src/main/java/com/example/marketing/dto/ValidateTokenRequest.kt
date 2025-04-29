package com.example.marketing.dto

data class ValidateTokenRequest(
    val jwtToken: String
) {
    companion object {
        fun of(jwtToken: String): ValidateTokenRequest {
            return ValidateTokenRequest(jwtToken = jwtToken)
        }
    }
}
