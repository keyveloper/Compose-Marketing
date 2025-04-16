package com.example.marketing.dto.keyword

data class GoldenKeywordStat(
    val keyword: String,
    val monthlySearchVolumePc: Int,
    val monthlySearchVolumeMobile: Int,
    val monthlyMeaningfulPostingVolume: Int,
    val mKEI: Double,
    val competition: String,  // naver ad 낮음, 보통, 높음 ...
    val blogVisitStat: List<BlogVisitStat>,
)