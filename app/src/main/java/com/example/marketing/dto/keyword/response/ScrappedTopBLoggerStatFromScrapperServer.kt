package com.example.marketing.dto.keyword.response

import com.example.marketing.domain.BlogVisitStat

data class ScrappedTopBLoggerStatFromScrapperServer(
    val keyword: String,
    val top10BlogVisitStat: List<BlogVisitStat>,
)


