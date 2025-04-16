package com.example.marketing.dto.keyword

data class GetGoldenKeywordsRequest(
    val keyword: String,
) {
    companion object {
        fun of(requestModel: FetchGoldenKeywords): GetGoldenKeywordsRequest {
            return GetGoldenKeywordsRequest(
                keyword = requestModel.keyword
            )
        }
    }
}