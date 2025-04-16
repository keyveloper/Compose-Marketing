package com.example.marketing.repository

import android.util.Log
import com.example.marketing.api.GoldenKeywordApi
import com.example.marketing.dto.keyword.FetchGoldenKeywords
import com.example.marketing.dto.keyword.GoldenKeywordStat
import com.example.marketing.exception.BusinessException
import javax.inject.Inject

class GoldenKeywordRepository @Inject constructor(
    private val goldenKeywordApi: GoldenKeywordApi
) {
    suspend fun fetchGoldenKeywords(requestModel: FetchGoldenKeywords): List<GoldenKeywordStat> {
        Log.i("goldenKeywordRepository", "request send")
        val response = goldenKeywordApi.fetchGoldenKeywords(requestModel)

        return if (response.frontErrorCode != 20000) {
            throw BusinessException(response.errorMessage)
        } else {
            response.goldenKeywords
        }
    }
}