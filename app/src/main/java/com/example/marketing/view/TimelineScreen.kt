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

@Composable
fun TimelineScreen(
    homeInnerPadding : PaddingValues
) {
    val advertisementItems = (1..30).map { i ->
        AdvertisementThumbnailItem(
            advertisementId = i.toLong(),
            thumbnailImageUrl = "",
            title = "Advertisement Title $i",
            itemName = "Item Name $i",
            channelType = ChannelType.BLOGGER, // 기본값, 필요에 따라 변경 가능
            reviewType = ReviewType.VISITED     // 기본값, 필요에 따라 변경 가능
        )
    }

    Column(
        modifier = Modifier.padding(
            top = homeInnerPadding.calculateTopPadding(),
            start = 15.dp,
            end = 15.dp
        )
            .background(Color.Cyan)
    ) {
        Text(
            text = "Advertisement",
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

@Preview(showBackground = true)
@Composable
fun PreviewTimelineScreen() {
}