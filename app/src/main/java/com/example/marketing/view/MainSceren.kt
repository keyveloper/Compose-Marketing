package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.marketing.ui.component.MainTopBar
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.marketing.enum.ChannelType
import com.example.marketing.enum.ReviewType
import com.example.marketing.ui.component.AdvertisementThumbnail
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.MainBottomBar
import com.example.marketing.ui.component.MainTopBar

@Composable
fun MainScreen() {
    // main controller
    val mainNavController = rememberNavController()


    val advertisementItems = (1..30).map { i ->
        AdvertisementThumbnailItem(
            advertisementId = i.toLong(),
            thumbnailImageUrl = "https://via.placeholder.com/170x180.png?text=Ad+$i",
            title = "Advertisement Title $i",
            itemName = "Item Name $i",
            channelType = ChannelType.BLOGGER, // 기본값, 필요에 따라 변경 가능
            reviewType = ReviewType.VISITED     // 기본값, 필요에 따라 변경 가능
        )
    }

    Scaffold(
        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 15.dp,
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 15.dp,
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(color = Color.LightGray)
            ) {
                Text("category Filed")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Advertisements",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(
                    count = advertisementItems.size / 2
                ) { rowIndex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        AdvertisementThumbnail(
                            item = advertisementItems[rowIndex * 2],
                            modifier = Modifier.weight(1f)
                        ) { }

                        Spacer(modifier = Modifier.width(16.dp))

                        AdvertisementThumbnail(
                            item = advertisementItems[rowIndex * 2 + 1],
                            modifier = Modifier.weight(1f)
                        ) { }

                    }

                }
            }

        }
    }
}


@Preview
@Composable
fun PreviewMainScrren() {
    MainScreen()
}