package com.example.marketing.dto.keyword

data class BlogVisitStat(
    val title: String,
    val bloggerId: String?,
    val blogUrl: String,
    val rank: Int,
    val avg5dVisitCount: Int,
    val max5dVisitCount: Int,
    val min5dVisitCount: Int
)