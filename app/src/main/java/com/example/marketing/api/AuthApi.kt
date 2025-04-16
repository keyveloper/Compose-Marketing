package com.example.marketing.api

import io.ktor.client.HttpClient
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient
) {
    // token - valid test ...
}