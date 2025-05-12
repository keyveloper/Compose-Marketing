package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.BlogVisitStat
import com.example.marketing.domain.DugKeywordCandidate
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.repository.GoldenKeywordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class GoldenKeywordDetailViewModel @Inject constructor(
    private val goldenKeywordRepository: GoldenKeywordRepository
): ViewModel() {
    // ------------ ⛏️ init ------------ ️
    private val _targetKeywordStat = MutableStateFlow<DugKeywordCandidate?> (null)
    val targetKeywordStat = _targetKeywordStat.asStateFlow()

    // ------------🔃 status ------------
    private val _topBlogVisitCallStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val topBlogVisitCallStatus = _topBlogVisitCallStatus.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _topBlogVisitStats = MutableStateFlow(listOf<BlogVisitStat>())
    val topBlogVisitStat = _topBlogVisitStats.asStateFlow()
    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun setTopBlogVisitStat() = run { _topBlogVisitStats.value = listOf() }

    fun addTopBlogVisitStata(stats: List<BlogVisitStat>) = run {
        _topBlogVisitStats.value += stats
    }

    fun updateTargetKeywordStat(keywordStat: DugKeywordCandidate) = run {
        _targetKeywordStat.value = keywordStat
    }
    // -------------🔍 inspection -----------
    fun isTargetSet(): Boolean = _targetKeywordStat.value != null

    // ----------- 🛜 API -----------------------
    suspend fun fetchTopBlogVisitStat() {
        if (isTargetSet()) {
            withContext(Dispatchers.IO) {
                val stats = goldenKeywordRepository.fetchScrappedTopBlogStat(
                    _targetKeywordStat.value!!.keyword
                )

                withContext(Dispatchers.Main) {
                    addTopBlogVisitStata(stats)
                    _topBlogVisitCallStatus.value = ApiCallStatus.SUCCESS
                }
            }
        }
    }
}