package com.example.marketing.exception

data class AdvertiserProfileFetchVariableNotSetException(
    override val viewmodelName: String,
    // write essential parameter
    val advertiserId: Long,
): EssentialVariableNotSetException(
    viewmodelName = viewmodelName
)
