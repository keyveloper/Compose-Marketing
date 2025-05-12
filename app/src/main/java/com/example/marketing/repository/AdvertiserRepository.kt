package com.example.marketing.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.marketing.api.AdvertiserApi
import com.example.marketing.api.AdvertiserImageApi
import com.example.marketing.api.AdvertiserProfileApi
import com.example.marketing.domain.AdvertiserProfileImage
import com.example.marketing.domain.AdvertiserProfileInfo
import com.example.marketing.dto.user.request.LoginAdvertiser
import com.example.marketing.dto.user.request.SaveAdvertiserProfileImage
import com.example.marketing.dto.user.request.SaveAdvertiserProfileInfo
import com.example.marketing.dto.user.request.SignUpAdvertiser
import com.example.marketing.dto.user.response.LoginAdvertiserInfo
import com.example.marketing.enums.ProfileImageType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdvertiserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val advertiserApi: AdvertiserApi,
    private val advertiserProfileApi: AdvertiserProfileApi,
    private val advertiserImageApi: AdvertiserImageApi
) {
    suspend fun signUp(requestModel: SignUpAdvertiser): Long? {
        val response = advertiserApi.signUp(requestModel)

        return if (response.frontErrorCode != 20000) {
            Log.i("advertiserRepo", "signUp failed: ${response.errorMessage}")
            null
        } else {
            response.createdId
        }
    }

    suspend fun login(requestModel: LoginAdvertiser): LoginAdvertiserInfo? {
        val response = advertiserApi.login(requestModel)

        return if (response.frontErrorCode != 20000) {
            Log.i("advertiserRepo", "login failed ${response.errorMessage}")
            null
        } else {
            LoginAdvertiserInfo(
                response.jwt,
                response.advertiserId
            )
        }
    }

    suspend fun uploadProfileImage(
        uri: Uri,
        profileImageType: ProfileImageType
    ): AdvertiserProfileImage? {
        val contentResolver = context.contentResolver
        val imageBytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalArgumentException("Cannot open input stream for $uri")
        val fileName = contentResolver
            .query(uri, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (cursor.moveToFirst()) cursor.getString(nameIndex) else "unknown.jpg"
            } ?: "unknown.jpg"
        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"

        val response = advertiserImageApi.uploadImage(
            requestModel = SaveAdvertiserProfileImage.of(
                originalFileName = fileName,
                imageType = mimeType,
                profileImageType = profileImageType
            ),
            imageBytes
        )

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.result
        }
    }

    suspend fun commitProfileImage(metaEntityId: Long): Long? {
        val response = advertiserImageApi.commitImage(metaEntityId)

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.result.metaEntityId
        }
    }

    suspend fun uploadProfileInfo(requestModel: SaveAdvertiserProfileInfo): Long? {
        val response = advertiserProfileApi.uploadProfileInfo(requestModel)

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.createdId
        }
    }

    suspend fun fetchProfileInfo(advertiserId: Long): AdvertiserProfileInfo? {
        val response = advertiserProfileApi.fetchProfileInfo(advertiserId)

        return if (response.frontErrorCode != 20000) {
            null
        } else {
            response.result
        }
    }
}