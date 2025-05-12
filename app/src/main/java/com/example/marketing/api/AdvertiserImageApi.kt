package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.user.request.MakeNewAdvertiserProfileImageRequest
import com.example.marketing.dto.user.request.SaveAdvertiserProfileImage
import com.example.marketing.dto.user.response.CommitAdvertiserProfileImageResponse
import com.example.marketing.dto.user.response.MakeNewAdvertiserProfileImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AdvertiserImageApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun uploadImage(
        requestModel: SaveAdvertiserProfileImage,
        binaryImage: ByteArray
    ): MakeNewAdvertiserProfileImageResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.submitFormWithBinaryData(
            url = "/advertiser/image/profile",
            formData = formData {
                append(
                    "meta",
                    Json.encodeToString(MakeNewAdvertiserProfileImageRequest.of(requestModel)),
                    Headers.build {
                        append(HttpHeaders.ContentType, "application/json")
                    }
                )
                append(
                    "file",
                    binaryImage,
                    Headers.build {
                        append(HttpHeaders.ContentType, requestModel.imageType)
                        append(
                            HttpHeaders.ContentDisposition,
                            """filename="${requestModel.originalFileName}""""
                        )
                    }
                )
            }
        ) {
            bearerAuth(token)
        }.body()
    }

    suspend fun commitImage(metaEntityId: Long): CommitAdvertiserProfileImageResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/advertiser/image/commit/$metaEntityId") {
            bearerAuth(token)
        }.body()
    }
}

