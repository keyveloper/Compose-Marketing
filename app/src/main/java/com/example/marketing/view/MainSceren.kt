package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.component.AdvertisementThumbnail
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.MainBottomBar
import com.example.marketing.ui.component.MainTopBar
import com.example.marketing.viewmodel.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    // status
    // HOME, LOCATION, FOLLOW, MY_PROFILE
    Scaffold(
        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar() }
    ) { innerPadding ->
        HomeScreen(
            innerPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = innerPadding.calculateBottomPadding()
            )
        )
    }

}


@Preview
@Composable
fun PreviewMyMainScreen() {
    MainScreen(MainViewModel())
}