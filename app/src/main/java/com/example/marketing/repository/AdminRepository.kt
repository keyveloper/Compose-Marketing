package com.example.marketing.repository

import com.example.marketing.api.AdminApi
import com.example.marketing.dto.user.request.LoginAdmin
import com.example.marketing.dto.user.request.SignUpAdmin
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val adminApi: AdminApi,
){
    suspend fun signUp(requestModel: SignUpAdmin): Long {
        val response = adminApi.signUp(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.createdId
        }
    }

    suspend fun login(requestModel: LoginAdmin): Long {
        val response = adminApi.login(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.adminId
        }
    }

}