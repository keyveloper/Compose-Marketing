package com.example.marketing.repository

import com.example.marketing.api.AdvertiserApi
import com.example.marketing.dto.user.request.LoginAdvertiser
import com.example.marketing.dto.user.request.SignUpAdvertiser
import com.example.marketing.domain.AdvertiserProfileResult
import com.example.marketing.dto.user.response.LoginAdvertiserInfo
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class AdvertiserRepository @Inject constructor(
    private val advertiserApi: AdvertiserApi
) {
    suspend fun signUp(requestModel: SignUpAdvertiser): Long {
        val response = advertiserApi.signUp(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.createdId
        }
    }

    suspend fun login(requestModel: LoginAdvertiser): LoginAdvertiserInfo {
        val response = advertiserApi.login(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            LoginAdvertiserInfo(
                response.jwt,
                response.advertiserId
            )
        }
    }

    suspend fun fetchProfile(advertiserId: Long): AdvertiserProfileResult {
        val response = advertiserApi.fetchProfile(advertiserId)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.result
        }
    }
}