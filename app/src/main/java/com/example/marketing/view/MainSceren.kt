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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.component.AdvertisementThumbnail
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.MainBottomBar
import com.example.marketing.ui.component.MainTopBar
import com.example.marketing.viewmodel.MainViewModel

@Composable
fun MainScreen(
) {
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
                .background(MaterialTheme.colorScheme.secondary)
                .align(Alignment.TopCenter)
        )

        HomeScreen( // 순서대로 안하면 topBar를 덮어버림
            modifier = Modifier
                .padding(
                    top = 56.dp,
                    bottom = 56.dp
                )
                .fillMaxSize()
                .background(Color.LightGray)
            ,
        )

        MainBottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .height(56.dp)
        )
    }
}


@Preview
@Composable
fun PreviewMyMainScreen() {
    MainScreen()
}