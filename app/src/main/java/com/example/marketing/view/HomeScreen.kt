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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.HomeScreenStatus
import com.example.marketing.enums.UserType
import com.example.marketing.ui.component.bar.HomeTopBar
import com.example.marketing.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    userId: Long,
    userType: UserType
) {
    // ------------✍️ input value -------------
    // ----------- 🚀 from server value -----------
    // ----------- 🎮 Controller-------------
    LaunchedEffect(userId) {
        viewModel.updateUserType(userType)
        viewModel.updateUserId(userId)
    }

    // ----------- 🛜 API -----------------------
    val screenState = viewModel.screenState.collectAsState()
    // status Screen Surface
    Box(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        // top bar
        HomeTopBar(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onHomeBarSelected = { selectedStatus ->  viewModel.changeState(selectedStatus) }
        )

        // content Screen Box
        Box(modifier = Modifier
            .padding(top = 68.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onTertiary)
        ) {
            when (screenState.value) {
                HomeScreenStatus.Event -> {
                    EventScreen(
                        navController = navController,
                        userId = userId,
                        userType = userType
                    )
                }
                
                HomeScreenStatus.Delivery -> {
                    // DeliveryScreen()
                }

                HomeScreenStatus.Type -> {

                }

                HomeScreenStatus.Platform -> {

                }
            }
        }
    }
}
