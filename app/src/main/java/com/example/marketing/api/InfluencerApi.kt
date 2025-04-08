package com.example.marketing.api

import io.ktor.client.HttpClient
import javax.inject.Inject

class InfluencerApi @Inject constructor(
    private val client: HttpClient
) {
}