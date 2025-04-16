package com.example.marketing.repository

import com.example.marketing.api.AdvertiserApi
import com.example.marketing.dto.user.request.LoginAdmin
import com.example.marketing.dto.user.request.LoginAdvertiser
import com.example.marketing.dto.user.request.SignUpAdvertiser
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class AdvertiseRepository @Inject constructor(
    private val advertiserApi: AdvertiserApi
) {
    suspend fun signUP(requestModel: SignUpAdvertiser): Long {
        val response = advertiserApi.signUp(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.createdId
        }
    }

    suspend fun login(requestModel: LoginAdvertiser): Long {
        val response = advertiserApi.login(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.advertiserId
        }
    }
}