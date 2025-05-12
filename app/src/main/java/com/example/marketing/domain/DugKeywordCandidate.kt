package com.example.marketing.domain

data class DugKeywordCandidate(
    val keyword: String,
    val competition: String,
    val monthlySearchVolumePc: Int,
    val monthlySearchVolumeMobile: Int,
)