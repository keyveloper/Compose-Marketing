package com.example.marketing.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketing.domain.InfluencerJoinedProfileInfo
import com.example.marketing.domain.InfluencerProfileImage
import com.example.marketing.dto.user.request.SaveInfluencerProfileInfo
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.repository.InfluencerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfluencerProfileEditViewModel @Inject constructor(
    private val influencerProfileRepository: InfluencerRepository
): ViewModel() {
    // ----------- ⛏️ init value --------------
    private val _profileInfo = MutableStateFlow<InfluencerJoinedProfileInfo?>(null)
    val profileInfo = _profileInfo.asStateFlow()

    // ------------✍️ input value -------------
    private val _profileImgUri = MutableStateFlow<Uri?>(null)
    val profileImgUri = _profileImgUri.asStateFlow()

    private val _job = MutableStateFlow<String?>(null)
    val job = _job.asStateFlow()

    private val _introduction = MutableStateFlow<String?>(null)
    val introduction = _introduction.asStateFlow()

    private val _blogUrl = MutableStateFlow<String?>(null)
    val blogUrl = _blogUrl.asStateFlow()

    private val _instagramUrl = MutableStateFlow<String?>(null)
    val instagramUrl = _instagramUrl.asStateFlow()

    private val _youtuberUrl = MutableStateFlow<String?>(null)
    val youtuberUrl = _youtuberUrl.asStateFlow()

    private val _threadUrl = MutableStateFlow<String?>(null)
    val threadUrl = _threadUrl.asStateFlow()

    // ------------🔃 status ------------
    private val _uploadInfoStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val uploadInfoStatus = _uploadInfoStatus.asStateFlow()

    private val _uploadImgStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val uploadImgStatus = _uploadImgStatus.asStateFlow()

    private val _commitStatus = MutableStateFlow(ApiCallStatus.IDLE)
    val commitStatus = _commitStatus.asStateFlow()

    // ----------- 🚀 api value -----------
    private val _imageMetaData = MutableStateFlow<InfluencerProfileImage?>(null)


    // ----------- 🎮 update function-------------
    fun init(info: InfluencerJoinedProfileInfo) = run {
        _profileInfo.value = info

        updateBlogUrl(info.blogUrl)
        updateInstagramUrl(info.instagramUrl)
        updateYoutuberUrl(info.youtuberUrl)
        updateThreadUrl(info.threadUrl)
    }


    fun updateProfileImgUri(uri: Uri?) = run {
        _profileImgUri.value = uri
    }

    fun updateJob(job: String) = run {
        _job.value = job
    }

    fun updateIntroduction(introduction: String) = run {
        _introduction.value = introduction
    }

    fun updateBlogUrl(url: String?) = run {
        _blogUrl.value = url
    }

    fun updateInstagramUrl(url: String?) = run {
        _instagramUrl.value = url
    }

    fun updateYoutuberUrl(url: String?) = run {
        _youtuberUrl.value = url
    }

    fun updateThreadUrl(url: String?) = run {
        _threadUrl.value = url
    }

    // ----------- 🛜 API -----------------------
    fun uploadAll() {
        viewModelScope.launch {
            val newUri = _profileImgUri.value

            // upload image
            newUri?.let {
                withContext(Dispatchers.IO) {
                    val profileImageMeta = influencerProfileRepository.uploadProfileImage(it)

                    if (profileImageMeta != null) {
                        withContext(Dispatchers.Main) {
                            _imageMetaData.value = profileImageMeta
                            _uploadImgStatus.value = ApiCallStatus.SUCCESS
                        }
                    }
                }
            }
            if (newUri == null) {
                _uploadImgStatus.value = ApiCallStatus.SUCCESS
            }

            // upload info
            withContext(Dispatchers.IO) {
                val createdId = influencerProfileRepository.uploadProfileInfo(
                    SaveInfluencerProfileInfo.of(
                        introduction = _introduction.value,
                        job = _job.value
                    )
                )
                if (createdId != null) {
                    withContext(Dispatchers.Main) {
                        _uploadInfoStatus.value = ApiCallStatus.SUCCESS
                    }
                }
            }


            if (_uploadImgStatus.value == ApiCallStatus.SUCCESS &&
                _uploadInfoStatus.value == ApiCallStatus.SUCCESS) {

                val meta = _imageMetaData.value

                if (meta != null) {
                    withContext(Dispatchers.IO) {
                        val commitedId = influencerProfileRepository.commitProfileImage(meta.id)

                        if (commitedId != null) {
                            _commitStatus.value = ApiCallStatus.SUCCESS
                        }
                    }
                } else {
                    _commitStatus.value = ApiCallStatus.SUCCESS
                }
            }
        }
    }
}