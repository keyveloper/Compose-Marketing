package com.example.marketing.state

sealed class SignUpAdminState {
    object Idle: SignUpAdminState()
    object Loading: SignUpAdminState()
    data class Success(val createdId: Long): SignUpAdminState()
    data class Error(val message: String): SignUpAdminState()
}