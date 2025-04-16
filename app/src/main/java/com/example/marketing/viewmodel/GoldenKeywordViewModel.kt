package com.example.marketing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.dto.keyword.FetchGoldenKeywords
import com.example.marketing.dto.keyword.GoldenKeywordStat
import com.example.marketing.enums.GoldenKeywordScreenStatus
import com.example.marketing.enums.GoldenKeywordFetchStatus
import com.example.marketing.exception.BusinessException
import com.example.marketing.repository.GoldenKeywordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoldenKeywordViewModel @Inject constructor(
    private val goldenKeywordRepository: GoldenKeywordRepository
): ViewModel() {
    private val _screenStatus = MutableStateFlow(GoldenKeywordScreenStatus.IDLE)
    val screenStatus = _screenStatus.asStateFlow()

    private val _fetchStatus = MutableStateFlow(GoldenKeywordFetchStatus.IDLE)
    val fetchStatus = _fetchStatus.asStateFlow()

    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword: StateFlow<String> =_searchKeyword.asStateFlow()

    private val _goldenKeywords = MutableStateFlow<List<GoldenKeywordStat>> (listOf())
    val goldenKeywords: StateFlow<List<GoldenKeywordStat>> = _goldenKeywords.asStateFlow()

    private val _selectedGoldenKeyword = MutableStateFlow<GoldenKeywordStat?> (null)
    val selectedGoldenKeyword: StateFlow<GoldenKeywordStat?> = _selectedGoldenKeyword.asStateFlow()

    fun updatedSearchKeyword(keyword: String) {
        _searchKeyword.value = keyword
    }

    fun updateSelectedKeyword(keyword: String) {
        _selectedGoldenKeyword.value = _goldenKeywords.value.find {it.keyword == keyword}
        updateScreenStatus(GoldenKeywordScreenStatus.VIEW_DETAIL)
    }

    fun updateScreenStatus(state: GoldenKeywordScreenStatus) {
        _screenStatus.value = state
    }

    private fun updateFetchStatus(state: GoldenKeywordFetchStatus) {
        _fetchStatus.value = state
    }

    private fun setGoldenKeywords(goldenKeywords: List<GoldenKeywordStat>) {
        _goldenKeywords.value = goldenKeywords
    }

    fun fetchGoldenKeywords() {
        viewModelScope.launch {
            val requestModel = FetchGoldenKeywords(keyword = _searchKeyword.value)

            try {
                Log.i("goldenKeywordViewModel", "start send request: ${requestModel}")
                updateFetchStatus(GoldenKeywordFetchStatus.LOADING)
                val goldenKeywords= goldenKeywordRepository.fetchGoldenKeywords(requestModel)
                viewModelScope.launch(Dispatchers.Main) {
                    setGoldenKeywords(goldenKeywords)
                    updateFetchStatus(GoldenKeywordFetchStatus.SUCCESS)
                    updateScreenStatus(GoldenKeywordScreenStatus.FETCHED)
                }
            } catch(e: BusinessException) {
                updateFetchStatus(GoldenKeywordFetchStatus.ERROR)
            } catch(e: Exception) {
                updateFetchStatus(GoldenKeywordFetchStatus.ERROR)
                Log.e("goldenKeywordViewModel", "${e.message}")
            }
        }
    }
}