package com.example.marketing.dto.keyword.request

data class GenerateSeoOptimizedTitlesRequest(
    val keyword: String,
    val description: String,
) {
    companion object {
        fun of(requestModel: GenerateSeoOptimizedTitles): GenerateSeoOptimizedTitlesRequest =
            GenerateSeoOptimizedTitlesRequest(
                keyword = requestModel.keyword,
                description = requestModel.description
            )
    }
}