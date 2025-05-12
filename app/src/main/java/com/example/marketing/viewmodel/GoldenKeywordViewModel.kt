package com.example.marketing.viewmodel

import androidx.compose.runtime.key
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.domain.DugKeywordCandidate
import com.example.marketing.dto.keyword.request.DigKeywordCandidates
import com.example.marketing.enums.GoldenKeywordFetchStatus
import com.example.marketing.enums.GoldenKeywordScreenStatus
import com.example.marketing.repository.GoldenKeywordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GoldenKeywordViewModel @Inject constructor(
    private val goldenKeywordRepository: GoldenKeywordRepository
): ViewModel() {

    // ------------✍️ input value -------------
    private val _keyword = MutableStateFlow<String?>(null)
    val keyword: StateFlow<String?> =_keyword.asStateFlow()

    private val _context = MutableStateFlow<String?> (null)
    val context = _context.asStateFlow()

    // ------------🔃 status ------------
    private val _fetchStatus = MutableStateFlow(GoldenKeywordFetchStatus.IDLE)
    val fetchStatus = _fetchStatus.asStateFlow()

    private val _screenStatus = MutableStateFlow(GoldenKeywordScreenStatus.INIT)
    val screenStatus = _screenStatus.asStateFlow()

    // ----------- 🚀 from server value -----------
    private val _candidates = MutableStateFlow(listOf<DugKeywordCandidate>())
    val candidates = _candidates.asStateFlow()

    // ---------------- [Function] -----------------------
    // ----------- 🎮 update function-------------
    fun updateKeyword(keyword: String) {
        _keyword.value = keyword
    }

    fun updateContext(context: String) {
        _context.value = context
    }

    fun updateFetchStatus(status: GoldenKeywordFetchStatus) {
        _fetchStatus.value = status
    }

    fun updateScreenStatus(status: GoldenKeywordScreenStatus) {
        _screenStatus.value = status
    }

    fun setCandidates() {
        _candidates.value = listOf()
    }
    private fun updateCandidates(candidates: List<DugKeywordCandidate>) {
        _candidates.value += candidates
    }



    // -------------🔍 inspection -----------
    fun canDig(): Boolean {
        val keyword = _keyword.value
        val context = _context.value

        return keyword != null && context != null &&
            keyword.isNotEmpty() && context.isNotEmpty()
    }

    // ----------- 🛜 API -----------------------
    fun digCandidates() = viewModelScope.launch {
        if (canDig()) {
            val keyword = _keyword.value!!
            val context = _context.value!!

            withContext(Dispatchers.IO) {
                    setCandidates()
                    val candidates = goldenKeywordRepository.fetchDugCandidates(
                        DigKeywordCandidates.of(
                            keyword = keyword,
                            context = context
                        )
                    )

                    if (candidates.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            updateCandidates(candidates)
                            updateScreenStatus(GoldenKeywordScreenStatus.CANDIDATES)
                        }
                    }

                    _fetchStatus.value = GoldenKeywordFetchStatus.SUCCESS
                }

        }
    }
}