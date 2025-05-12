package com.example.marketing.dto.keyword.response

import com.example.marketing.domain.DugKeywordCandidate


data class DigKeywordCandidatesResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val candidates: List<DugKeywordCandidate>
)

