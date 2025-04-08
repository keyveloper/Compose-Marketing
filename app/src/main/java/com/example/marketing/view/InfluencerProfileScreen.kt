package com.example.marketing.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose .runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.*
import com.example.marketing.R
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.viewmodel.InfluencerProfileViewModel

@Composable
fun InfluencerProfileScreen(
    viewModel: InfluencerProfileViewModel = hiltViewModel()
) {
    val minHeight: Dp = 120.dp
    val maxHeight: Dp = 250.dp
    val density = LocalDensity.current

    val heightPx = with(density) { maxHeight.toPx() - minHeight.toPx() }
    var offset by remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = offset + delta
                offset = newOffset.coerceIn(-heightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val influencer = viewModel.influencer.collectAsState()

    val animatedHeight by animateDpAsState(
        targetValue = maxHeight + with(density) { offset.toDp() },
        label = "HeaderHeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        // Scrollable Background Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight)
                .background(Color.Gray)
        ) {
            // Put profile image or overlay here if needed
        }

        Image(
            painter = painterResource(id = ChannelIcon.BLOGGER.painterId), // Replace with your image
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopStart)
                .offset {
                    IntOffset(
                        x = with(density) { 16.dp.toPx() }.toInt(),
                        y = with(density) { (animatedHeight - 60.dp).toPx() }.toInt()
                    )
                }// 60.dp = half of image size
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .padding(16.dp)
        )


        // Rest of the content (not scrollable with background)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    top = animatedHeight + 60.dp / 2,
                    start = 16.dp,
                    end = 16.dp
                    ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Spacer(Modifier.height(32.dp))
                // UserID
                influencer.value?.let {
                    Text(it.loginId)
                } ?: Text(
                    "Login ID"
                )
            }
            // Add more UI content...

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 8.dp,
                    color = Color.LightGray
                )

                // about
                influencer.value?.let {
                    Text(it.introduction)
                } ?: Text("Introduction")
            }

            item {
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                        .background(Color.LightGray)
                )

                // Channel..
            }
        }
    }
}