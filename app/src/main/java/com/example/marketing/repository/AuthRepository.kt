package com.example.marketing.repository

import android.util.Log
import com.example.marketing.api.AuthApi
import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.ValidateTokenResult
import com.example.marketing.entity.JwtTokenEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val dao: JwtTokenDao
){
    fun observeToken(): Flow<String?> =
        dao.observeToken()
            .map { it?.token }
            .onEach { Log.i("authRepository", "onEach token = $it") }

    suspend fun clear() = dao.clear()

    suspend fun saveToken(jwt: String) = dao.upsert(JwtTokenEntity(token = jwt))

    suspend fun validateToken(token: String): ValidateTokenResult? {
        val response = authApi.validateToken(token)

        return if (response.frontErrorCode == 20000) {
            response.validateResult
        } else {
            Log.i("authRepository", "valid test failed")
            null
        }
    }
}