package com.example.marketing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketing.enums.MainScreenStatus
import com.example.marketing.enums.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
): ViewModel() {
    private val _screenStatus = MutableStateFlow(MainScreenStatus.HOME)
    val screenStatus: StateFlow<MainScreenStatus> = _screenStatus.asStateFlow()

    private val _userType = MutableStateFlow(UserType.INFLUENCER)
    val userType: StateFlow<UserType> = _userType.asStateFlow()
    
    private val _userId = MutableStateFlow(-1L)
    val userId = _userId.asStateFlow()

    fun updateScreenStatus(status: MainScreenStatus) {
        _screenStatus.value = status
    }

    fun updateUserType(status: UserType) {
        _userType.value = status
    }

    fun updateUserId(id: Long) {
        _userId.value = id
    }


}