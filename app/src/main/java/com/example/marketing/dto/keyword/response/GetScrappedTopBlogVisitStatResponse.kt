package com.example.marketing.dto.keyword.response

data class GetScrappedTopBlogVisitStatResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer,
)


