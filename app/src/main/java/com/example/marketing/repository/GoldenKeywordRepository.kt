package com.example.marketing.repository

import android.util.Log
import com.example.marketing.api.GoldenKeywordApi
import com.example.marketing.domain.BlogVisitStat
import com.example.marketing.domain.DugKeywordCandidate
import com.example.marketing.dto.keyword.request.DigKeywordCandidates
import com.example.marketing.dto.keyword.request.GenerateSeoOptimizedTitles
import javax.inject.Inject

class GoldenKeywordRepository @Inject constructor(
    private val goldenKeywordApi: GoldenKeywordApi
) {
    suspend fun fetchDugCandidates(
        requestModel: DigKeywordCandidates
    ): List<DugKeywordCandidate> {
        Log.i("goldenKeywordRepository", "request send")
        val response = goldenKeywordApi.fetchDugCandidates(requestModel)

        return if (response.frontErrorCode != 20000) {
            Log.e("goldenKeywordRepository", "fetch dug candidates failed ")
            listOf()
        } else {
            response.candidates
        }
    }

    suspend fun fetchScrappedTopBlogStat(keyword: String): List<BlogVisitStat> {
        val response = goldenKeywordApi.fetchScrappedTopBlogStat(keyword)

        return if (response.frontErrorCode != 20000) {
            Log.e("goldenKeywordRepository", "fetch Scrapped Blog stat failed ")
            listOf()
        } else {
            response.topBloggerStat.top10BlogVisitStat
        }
    }

    suspend fun generateSeoOptimizedTitles(
        requestModel: GenerateSeoOptimizedTitles)
    : List<String> {
        val response = goldenKeywordApi.generateSeoOptimizedTitles(requestModel)

        return if (response.frontErrorCode != 20000) {
            Log.e("goldenKeywordRepository", "generate titiles failed")
            listOf()
        } else {
            response.titles
        }
    }
}