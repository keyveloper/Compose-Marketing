package com.example.marketing.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Output
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ProfileMode
import com.example.marketing.viewmodel.AdvertiserProfileControlViewModel
import kotlinx.coroutines.launch

@Composable
fun AdvertiserProfileControlScreen(
    viewModel: AdvertiserProfileControlViewModel = hiltViewModel(),
    navController: NavController,
) {
    // ------------🔃 status ------------
    val profileMode by viewModel.profileMode.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // ----------- 🚀 api value -----------
    val profileInfo by viewModel.profileInfo.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(Unit) {
        if (profileMode == ProfileMode.INIT) {
            viewModel.fetchProfileInfo()    // this is suspend function : sync
        }
    }

    when(profileMode) {
        ProfileMode.INIT -> {
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


                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .zIndex(5f),
                    onClick = {
                        coroutineScope.launch {
                            viewModel.logOut()
                        }
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Default.Output,
                        contentDescription = "influencer logout"
                    )
                }

                AdvertiserProfileScreen(
                    navController = navController,
                    profileInfo = profileInfo!!
                )
            }
        }

        ProfileMode.EDIT -> {

        }
    }

}