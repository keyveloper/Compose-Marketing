package com.example.marketing.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.marketing.domain.InfluencerJoinedProfileInfo
import com.example.marketing.viewmodel.InfluencerProfileViewModel

@Composable
fun InfluencerProfileScreen(
    viewModel: InfluencerProfileViewModel = hiltViewModel(),
    initProfileInfo: InfluencerJoinedProfileInfo
) {
    // ------------✍️ input value -------------
    // ------------🔃 status ------------
    // ----------- 🚀 api value -----------
    val profileInfo by viewModel.profileInfo.collectAsState()
    val profileImage by viewModel.fetchedProfileImageByte.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(profileInfo) {
        viewModel.init(initProfileInfo)
        viewModel.fetchImageBytes()
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

        AsyncImage(
            model = profileImage, // Replace with your image
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
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
        )


        // Rest of the content (not scrollable with background)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    top = animatedHeight + 60.dp / 2,
                    start = 24.dp,
                    end = 24.dp
                    )
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // User info Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .padding(top = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        profileInfo?.let {
                            Text(
                                text = "@${it.influencerLoginId}",
                                style = MaterialTheme.typography.displayMedium
                            )
                        } ?: Text(
                            text = "user Id",
                            style = MaterialTheme.typography.displayMedium
                        )

                        Text(
                            text = profileInfo?.job ?: "no job",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
            // Add more UI content...
            item {
                // user introduction & url ...
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = profileInfo?.introduction ?: "introduction",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color(0x07070707),
                                    shape = RoundedCornerShape(3.dp) // optional rounded corners
                                )
                                .padding(16.dp) // give inner content some breathing room
                        ) {
                            Text(
                                text = "😎 채널",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }

            item {
                // reviewd
            }
        }
    }
}