package com.example.marketing.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.*
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.component.AdvertisementThumbnail
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.HomeTopBar
import com.example.marketing.ui.component.MainBottomBar
import com.example.marketing.ui.component.MainTopBar

@Composable
fun HomeScreen(
    innerPadding: PaddingValues
) {
    // status Screen Surface
    Surface(
        modifier = Modifier.padding(innerPadding)
            .background(Color.Green)
    ) {
        HomeTopBar()
        TimelineScreen(
            PaddingValues(
                top = 40.dp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
   }