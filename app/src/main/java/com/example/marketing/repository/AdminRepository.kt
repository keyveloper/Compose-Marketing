package com.example.marketing.repository

import com.example.marketing.api.AdminApi
import com.example.marketing.dto.user.request.SignUpAdmin
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val adminApi: AdminApi,
){

    suspend fun signUp(requestModel: SignUpAdmin) {
        adminApi.signUp(requestModel)
    }
}