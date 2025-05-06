package com.example.marketing.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.marketing.api.AdvertisementImageApi
import com.example.marketing.domain.AdvertisementImage
import com.example.marketing.dto.board.request.SaveAdvertisementImageMetadata
import com.example.marketing.dto.board.request.SetAdvertisementThumbnail
import com.example.marketing.exception.BusinessException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdvertisementImageRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val advertisementImageApi: AdvertisementImageApi
){
    suspend fun uploadImage(uri: Uri, draftId: Long): AdvertisementImage {
        val contentResolver = context.contentResolver
        val imageBytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalArgumentException("Cannot open input stream for $uri")

        val fileName = contentResolver
            .query(uri, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (cursor.moveToFirst()) cursor.getString(nameIndex) else "unknown.jpg"
            } ?: "unknown.jpg"

        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"

        val response = advertisementImageApi.upload(
            SaveAdvertisementImageMetadata.of(fileName, mimeType, draftId),
            imageBytes
        )

        return if (response.frontErrorCode == 20000) {
            Log.i("adImageRepo", "upload-result: ${response.result}")
            response.result
        } else {
            throw BusinessException(response.errorMessage)
        }
    }

    suspend fun withdrawUpload(metadataId: Long) {
        val response = advertisementImageApi.withDrawUploading(metadataId)

        if (response.frontErrorCode != 20000) {
            Log.i("adImageRepo", "withdrawFailed...")
        }
    }

    suspend fun setThumbnail(imageEntityId: Long, advertisementId: Long): Boolean {
        val response = advertisementImageApi.setThumbnail(
            SetAdvertisementThumbnail.of(
                imageId = imageEntityId,
                advertisementId = advertisementId
            )
        )

        return if (response.frontErrorCode != 20000) {
            Log.i("adImageRepo", "withdrawFailed...")
            false
        } else true
    }

    suspend fun setAdvertisementId(draftId: Long): Boolean {
        val response = advertisementImageApi.setAdvertisementId(draftId)
        return if (response.frontErrorCode != 20000) {
            Log.i("adImageRepo", "withdrawFailed...")
            false
        } else true
    }


    suspend fun fetchImageBytes(url: String?): ByteArray? {
        return if (url == null) {
            Log.w("adImgRepo", "fetch image bytes - url is null...")
            null
        } else {
            val response = advertisementImageApi.fetchImageByteByIdentifiedUrl(url)
            response
        }
    }
}