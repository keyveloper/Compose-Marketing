package com.example.marketing.dto.keyword.request

data class DigKeywordCandidates(
    val keyword: String,
    val context: String
) {
    companion object {
        fun of(
            keyword: String,
            context: String
        ): DigKeywordCandidates = DigKeywordCandidates(
            keyword = keyword,
            context = context
        )
    }
}
