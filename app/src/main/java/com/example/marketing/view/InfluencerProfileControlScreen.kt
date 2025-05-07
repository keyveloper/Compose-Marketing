package com.example.marketing.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.ProfileMode
import com.example.marketing.viewmodel.InfluencerProfileControlViewModel

@Composable
fun InfluencerProfileControlScreen(
    viewModel: InfluencerProfileControlViewModel = hiltViewModel(),
    influencerId: Long
) {

    // ------------🔃 status ------------
    val profileMode by viewModel.profileMode.collectAsState()

    // ----------- 🚀 api value -----------
    val profileInfo by viewModel.profileInfo.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(profileMode, influencerId) {
        if (profileMode == ProfileMode.INIT) {
            viewModel.updateInfluencerId(influencerId)    // have your VM advance mode to READ_ONLY
            viewModel.fetchProfileInfo()    // this can be suspend or non-suspend
        }
    }

    when(profileMode) {
        ProfileMode.INIT -> {
            // showing indicator
        }

        ProfileMode.READ_ONLY -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                IconButton( // 😎 오 이렇게 두면 상단 고정되네 ??
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .zIndex(5f),
                    onClick = {
                        viewModel.updateProfileMode(ProfileMode.EDIT)
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Default.ModeEdit,
                        contentDescription = "profile modify button"
                    )
                }

                InfluencerProfileScreen(
                    initProfileInfo = profileInfo!! //📌 after fetch (always_
                )
            }
        }

        ProfileMode.EDIT -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                IconButton( // 😎 오 이렇게 두면 상단 고정되네 ??
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .zIndex(5f),
                    onClick = {
                        viewModel.updateProfileMode(ProfileMode.READ_ONLY)
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "profile info edit save"
                    )
                }


                InfluencerProfileEditScreen(
                    initProfileInfo = profileInfo!! //📌 after fetch (always_
                )
            }
        }
    }
}