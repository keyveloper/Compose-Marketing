package com.example.marketing.state

import com.example.marketing.domain.AdvertisementPackage

sealed interface AdDetailInitFetchedState {
    object Loading : AdDetailInitFetchedState
    data class Ready(
        val pkg: AdvertisementPackage
    ): AdDetailInitFetchedState
    data class Error(val msg: String): AdDetailInitFetchedState
}