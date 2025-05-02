package com.example.marketing.api

import android.util.Log
import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import com.example.marketing.dto.board.response.MakeNewAdvertisementImageResponse
import com.example.marketing.dto.board.request.SaveAdvertisementImageMetadata
import com.example.marketing.dto.board.request.SetAdvertisementThumbnail
import com.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import com.example.marketing.dto.board.response.DeleteAdImageResponse
import com.example.marketing.dto.board.response.GetAllAdImageMetadataResponse
import com.example.marketing.dto.board.response.SetAdvertisementThumbnailResponse
import com.example.marketing.dto.board.response.SetImageAdvertisementResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.encodeToString
import javax.inject.Inject
import kotlinx.serialization.json.Json


class AdvertisementImageApi @Inject constructor(
    private val httpClient: HttpClient,
    private val jwtTokenDao: JwtTokenDao
) {

    suspend fun upload(
        requestModel: SaveAdvertisementImageMetadata,
        binaryImage: ByteArray,
    ): MakeNewAdvertisementImageResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.submitFormWithBinaryData(
            url = "/advertisement/image",
            formData = formData {
                // meta
                append(
                    "meta",
                    Json.encodeToString(MakeNewAdvertisementImageRequest.of(requestModel)),
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

    suspend fun withDrawUploading(metadataId: Long): DeleteAdImageResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        return httpClient.delete("/advertisement/image/$metadataId") {
            bearerAuth(token)
        }.body()
    }

    suspend fun setThumbnail(requestModel: SetAdvertisementThumbnail)
    : SetAdvertisementThumbnailResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""
        Log.i("AdImageApi", "imgEntityId: ${requestModel.imageId}")

        return httpClient.post("/advertisement/image/thumbnail") {
            setBody(
                SetAdvertisementThumbnailRequest.of(requestModel)
            )
            bearerAuth(token)
        }.body()
    }

    suspend fun setAdvertisementId(draftId: Long): SetImageAdvertisementResponse {
        val token = jwtTokenDao.observeToken().firstOrNull()?.token ?: ""

        return httpClient.post("/advertisement/image/publisher/$draftId") {
            bearerAuth(token)
        }.body()
    }

    suspend fun fetchAllMetaDataByAdvertisementId(advertisementId: Long):
            GetAllAdImageMetadataResponse {
        return httpClient.get("/open/advertisement/image/meta/$advertisementId").body()
    }

    suspend fun fetchByIdentifiedUrl(imageUrl: String): ByteArray {
        return httpClient.get("/open/$imageUrl").body()
    }
}