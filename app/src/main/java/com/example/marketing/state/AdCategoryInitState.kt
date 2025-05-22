package com.example.marketing.state

import com.example.marketing.domain.AdvertisementPackage

sealed interface AdCategoryInitState {
    object Loading: AdCategoryInitState
    data class Ready(
        val pkgs: List<AdvertisementPackage>
    ): AdCategoryInitState
    data class Error(val msg: String): AdCategoryInitState
}