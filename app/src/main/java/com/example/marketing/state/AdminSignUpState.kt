package com.example.marketing.state


sealed class AdminSignUpState {
    object Idle: AdminSignUpState()
    object Loading: AdminSignUpState()
    data class Success(val createdId: Long): AdminSignUpState()
    data class Error(val message: String): AdminSignUpState()
}