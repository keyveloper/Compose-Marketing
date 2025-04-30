package com.example.marketing.repository

import android.content.Context
import com.example.marketing.api.AdvertisementImageApi
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val advertisementImageApi: AdvertisementImageApi
) {

}