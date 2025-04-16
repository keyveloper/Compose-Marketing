package com.example.marketing.dto.keyword

data class GetGoldenKeywordsResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val goldenKeywords: List<GoldenKeywordStat>,
)