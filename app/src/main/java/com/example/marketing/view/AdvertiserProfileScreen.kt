package com.example.marketing.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.example.marketing.ui.color.PastelPea
import com.example.marketing.viewmodel.AdvertiserProfileViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Composable
fun AdvertiserProfileScreen(
    viewModel: AdvertiserProfileViewModel = hiltViewModel(),
    initAdvertiserId: Long
) {
    val advertiserId = viewModel.advertiserId.collectAsState()
    val profile = viewModel.profile.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateAdvertiserId(initAdvertiserId)
        snapshotFlow { advertiserId.value }
            .filter { it != -1L }
            .first()
            .let { viewModel.fetchProfile(it) }
    }

    val minHeight: Dp = 120.dp
    val maxHeight: Dp = 250.dp
    val density = LocalDensity.current

    val heightPx = with(density) { maxHeight.toPx() - minHeight.toPx() }
    var offset by remember { mutableFloatStateOf(0f) }

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
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(250.dp)
                        .background(PastelPea)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = profile.value?.companyName ?: "",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = profile.value?.companyInfo ?: "",
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = profile.value?.companyLocation ?: "",
                            style = MaterialTheme.typography.displaySmall
                        )

                        Text(
                            text = profile.value?.followerCount.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                        .background(PastelPea)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = profile.value?.introduction ?: "",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
                        .background(PastelPea)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Advertisements",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {}
                            ) {
                                Text(
                                    text = "진행중인 광고",
                                )
                            }

                            Button(
                                onClick = {}
                            ) {
                                Text(
                                    text = "완료된 광고"
                                )
                            }
                        }
                    }
                }
            }

            items(1) {
                // advertisement...
            }
        }

    }
}