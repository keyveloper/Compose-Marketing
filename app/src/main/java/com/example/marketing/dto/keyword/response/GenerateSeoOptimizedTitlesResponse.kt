package com.example.marketing.dto.keyword.response

data class GenerateSeoOptimizedTitlesResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val titles: List<String>
)