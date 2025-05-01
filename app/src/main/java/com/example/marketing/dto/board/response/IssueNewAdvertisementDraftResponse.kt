package com.example.marketing.dto.board.response

import com.example.marketing.domain.AdvertisementDraft

data class IssueNewAdvertisementDraftResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val advertisementDraft: AdvertisementDraft
)

