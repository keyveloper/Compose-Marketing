package com.example.marketing.dto.keyword.request

data class GetScrappedTopBlogVisitStatRequest(
    val keyword: String,
) {
    companion object {
        fun of(keyword: String): GetScrappedTopBlogVisitStatRequest =
            GetScrappedTopBlogVisitStatRequest(
                keyword
            )
    }
}
