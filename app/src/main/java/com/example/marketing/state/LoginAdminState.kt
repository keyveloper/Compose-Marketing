package com.example.marketing.state

sealed class LoginAdminState {
    object Idle: LoginAdminState()
    object Loading: LoginAdminState()
    data class Success(val loginId: Long): LoginAdminState()
    data class Error(val message: String): LoginAdminState()
}