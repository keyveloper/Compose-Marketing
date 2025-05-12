package com.example.marketing.dto.keyword.request


data class DigKeywordCandidatesRequest(
    val keyword: String,
    val context: String
) {
    companion object {
        fun of(
            requestModel: DigKeywordCandidates
        ): DigKeywordCandidatesRequest = DigKeywordCandidatesRequest(
            keyword = requestModel.keyword,
            context = requestModel.context
        )
    }
}

