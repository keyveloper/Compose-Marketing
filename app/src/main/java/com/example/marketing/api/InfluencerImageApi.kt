package com.example.marketing.api

import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.user.request.MakeNewInfluencerProfileImageRequest
import com.example.marketing.dto.user.request.SaveInfluencerProfileImage
import com.example.marketing.dto.user.response.CommitInfluencerProfileImageResponse
import com.example.marketing.dto.user.response.MakeNewInfluencerProfileImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import javax.inject.Inject

class InfluencerImageApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {
    suspend fun uploadProfileImage(
        requestModel: SaveInfluencerProfileImage,
        binaryImage: ByteArray
    ): MakeNewInfluencerProfileImageResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.submitFormWithBinaryData(
            url = "/influencer/image/profile",
            formData = formData {
                append(
                    "meta",
                    Json.encodeToString(MakeNewInfluencerProfileImageRequest.of(requestModel)),
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

    suspend fun commitProfileImage(profileImageId: Long): CommitInfluencerProfileImageResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/influencer/image/profile/commit/$profileImageId") {
            bearerAuth(token)
        }.body()
    }

    suspend fun fetchProfileImageByte(unifiedCode: String): ByteArray {
        return httpClient.get("/open/influencer/image/profile/$unifiedCode").body()
    }
}