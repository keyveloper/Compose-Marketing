package com.example.marketing.repository

import android.util.Log
import com.example.marketing.api.InfluencerApi
import com.example.marketing.dto.user.request.LoginInfluencer
import com.example.marketing.dto.user.request.SignUpInfluencer
import com.example.marketing.dto.user.response.LoginInfluencerResult
import javax.inject.Inject

class InfluencerRepository @Inject constructor(
    private val influencerApi: InfluencerApi
) {
    suspend fun login(loginId: String, password: String): LoginInfluencerResult? {
        val response = influencerApi.login(
            LoginInfluencer.of(loginId, password)
        )

        return if (response.frontErrorCode != 20000) {
            Log.i("influencerRepo", "login-login failed")
            null
        } else {
            response.result
        }
    }
    
    suspend fun signUp(requestModel: SignUpInfluencer): Long? {
        val response = influencerApi.signUp(requestModel)

        return if (response.frontErrorCode != 20000) {
            Log.i("influencerRepo", "signUp failed")
            null
        } else {
            response.CreatedId
        }
    }
}