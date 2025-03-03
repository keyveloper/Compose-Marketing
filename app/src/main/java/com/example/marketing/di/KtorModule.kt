package com.example.marketing.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 3000000   // 30초 * 100
                connectTimeoutMillis = 3000000   // 30초 * 100
                socketTimeoutMillis = 1500000  // 소켓 시간 15초 * 100
            }

            install(DefaultRequest) {
                contentType(ContentType.Application.Json)

                url {
                    protocol = URLProtocol.HTTP
                    host = "10.0.2.2"
                    port = 8080
                }
                header("User-Agent", "Marketing app")
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor Http Log", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}