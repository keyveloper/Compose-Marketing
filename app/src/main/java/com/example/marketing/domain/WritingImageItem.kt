package com.example.marketing.domain

import android.net.Uri

data class WritingImageItem(
    val localUri: Uri,
    val domain: AdvertisementImage?,
    val uploaded: Boolean,
) {
    companion object {
        fun of(
            localUri: Uri,
            domain: AdvertisementImage?,
            uploaded: Boolean
        ): WritingImageItem = WritingImageItem(localUri, domain, uploaded)
    }
}
