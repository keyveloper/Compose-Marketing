package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.MainScreenStatus
import com.example.marketing.enums.UserType
import com.example.marketing.ui.component.bar.MainBottomBar
import com.example.marketing.ui.component.bar.MainTopBar
import com.example.marketing.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    userInitType: UserType,
    initUserId: Long,
    navController: NavController
) {
    val screenStatus = viewModel.screenStatus.collectAsState()
    val userType = viewModel.userType.collectAsState()
    val userId = viewModel.userId.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateUserType(userInitType)
        viewModel.updateUserId(initUserId)
    }

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
        when (screenStatus.value) {
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
                    navController = navController,
                    userId = userId.value,
                    userType = userType.value
                )
            }

            MainScreenStatus.FOLLOW -> {

            }

            MainScreenStatus.PROFILE -> {
                when (userType.value) {
                    UserType.INFLUENCER -> {
                        InfluencerProfileControlScreen(
                            influencerId = userId.value,
                        )
                    }

                    UserType.ADVERTISER_COMMON -> {
                        AdvertiserProfileScreen(
                            initAdvertiserId = userId.value
                        )
                    }

                    UserType.ADMIN -> {

                    }

                    else -> {

                    }
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

            MainScreenStatus.ADVERTISER_WRITE -> {
                AdvertisementWritingScreen(
                    modifier = Modifier
                        .padding(
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
                viewModel.updateScreenStatus(status)
            },
            userType = userType.value
        )
    }
}
