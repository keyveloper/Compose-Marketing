package com.example.marketing.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.viewmodel.InfluencerProfileViewModel

@Composable
fun InfluencerProfileScreen(
    viewModel: InfluencerProfileViewModel = hiltViewModel(),
    influencerId: Long
) {


    // ------------✍️ input value -------------
    // ------------🔃 status ------------
    // ----------- 🚀 api value -----------
    val profileInfo by viewModel.profileInfo.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(influencerId) {
        viewModel.updateInfluencerId(influencerId)
        viewModel.fetchProfileInfo()
    }


    // ----------- 🖼️ uI ---------------
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
                profileInfo?.let {
                    Text(it.influencerLoginId)
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
                profileInfo?.let {
                    Text(it.introduction?: "Introduction")
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