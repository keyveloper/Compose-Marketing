package com.example.marketing.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.marketing.api.InfluencerApi
import com.example.marketing.api.InfluencerImageApi
import com.example.marketing.api.InfluencerProfileApi
import com.example.marketing.domain.InfluencerJoinedProfileInfo
import com.example.marketing.domain.InfluencerProfileImage
import com.example.marketing.dto.user.request.LoginInfluencer
import com.example.marketing.dto.user.request.SaveInfluencerProfileImage
import com.example.marketing.dto.user.request.SaveInfluencerProfileInfo
import com.example.marketing.dto.user.request.SignUpInfluencer
import com.example.marketing.dto.user.response.LoginInfluencerResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InfluencerRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val influencerApi: InfluencerApi,
    private val influencerImageApi: InfluencerImageApi,
    private val influencerProfileApi: InfluencerProfileApi
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

    suspend fun uploadProfileImage(uri: Uri): InfluencerProfileImage? {
        val contentResolver = context.contentResolver
        val imageBytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalArgumentException("Cannot open input stream for $uri")
        val fileName = contentResolver
            .query(uri, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (cursor.moveToFirst()) cursor.getString(nameIndex) else "unknown.jpg"
            } ?: "unknown.jpg"
        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"

        val response = influencerImageApi.uploadProfileImage(
            SaveInfluencerProfileImage.of(fileName, mimeType),
            imageBytes
        )

        return if (response.frontErrorCode == 20000) {
            response.result
        } else {
            Log.i("influRepo", "upload profile image failed ${response.errorMessage}}")
            null
        }
    }

    suspend fun commitProfileImage(imageEntityId: Long): Long? {
        val response = influencerImageApi.commitProfileImage(imageEntityId)

        return if (response.frontErrorCode != 20000) {
            Log.i("influRepo", "commit profile image failed ${response.errorMessage}}")
            null
        } else {
            response.result.id
        }
    }

    suspend fun fetchProfileImageByte(unifiedCode: String): ByteArray {
        return influencerImageApi.fetchProfileImageByte(unifiedCode)
    }

    suspend fun uploadProfileInfo(requestModel: SaveInfluencerProfileInfo): Long? {
        val response = influencerProfileApi.uploadProfileInfo(requestModel)

        return if (response.frontErrorCode != 20000) {
            Log.i("influRepo", "upload profile info failed ${response.errorMessage}}")
            null
        } else {
            response.createdId
        }
    }

    suspend fun fetchProfileInfo(influencerId: Long): InfluencerJoinedProfileInfo? {
        val response = influencerProfileApi.fetchProfileInfo(influencerId)

        return if (response.frontErrorCode != 20000) {
            Log.i("influRepo", "fetch failed ${response.errorMessage}}")
            null
        } else {
            response.result
        }
    }
}