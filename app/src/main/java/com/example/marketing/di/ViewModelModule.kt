package com.example.marketing.di

import com.example.marketing.enums.HomeScreenStatus
import com.example.marketing.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun provideHomeViewModel(): HomeViewModel {
        return HomeViewModel()
    }
}