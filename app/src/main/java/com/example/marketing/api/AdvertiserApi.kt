package com.example.marketing.api

import io.ktor.client.HttpClient
import javax.inject.Inject

class AdvertiserApi @Inject constructor(
    private val httpClient: HttpClient
) {
}