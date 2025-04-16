package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.marketing.enums.MainScreenStatus
import com.example.marketing.enums.UserStatus
import com.example.marketing.ui.component.bar.MainBottomBar
import com.example.marketing.ui.component.bar.MainTopBar
import com.example.marketing.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val mainStatus = viewModel.mainStatus.collectAsState()
    val userStatus = viewModel.userStatus.collectAsState()

    // status
    // HOME, LOCATION, FOLLOW, MY_PROFILE
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 50.dp)
    ) {
        MainTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.onSecondary)
                .align(Alignment.TopCenter),
        )
        when (mainStatus.value) {

            MainScreenStatus.LOCATION_NEAR -> {

            }

            MainScreenStatus.LOCATION_MAP -> {

            }

            MainScreenStatus.HOME -> {
                HomeScreen( // 순서대로 안하면 topBar를 덮어버림
                    modifier = Modifier
                        .padding(
                            top = 56.dp,
                            bottom = 56.dp,
                        )
                        .fillMaxSize()
                        .background(Color.White)
                    ,
                )
            }

            MainScreenStatus.FOLLOW -> {

            }

            MainScreenStatus.PROFILE -> {
                if (userStatus.value == UserStatus.INFLUENCER) {
                    InfluencerProfileScreen()
                }
            }

            MainScreenStatus.GOLDEN -> {
                GoldenKeywordScreen(
                    modifier = Modifier
                        .padding(
                            top = 56.dp,
                            bottom = 56.dp
                        )
                        .fillMaxSize()
                        .background(Color.White)
                )
            }
        }


        MainBottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .height(56.dp),
            onStatusChange = { status ->
                viewModel.changeStatus(status)
            }
        )
    }
}
