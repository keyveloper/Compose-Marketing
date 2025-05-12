package com.example.marketing.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.domain.AdvertiserProfileInfo
import com.example.marketing.enums.AdvertiserProfileAdMode
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.ui.color.PastelPea
import com.example.marketing.ui.color.PastelSkyBlue
import com.example.marketing.ui.color.SeaFoam
import com.example.marketing.ui.color.SeaGreen
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.VerticalAdvertisementThumbnail
import com.example.marketing.viewmodel.AdvertiserProfileViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun AdvertiserProfileScreen(
    viewModel: AdvertiserProfileViewModel = hiltViewModel(),
    navController: NavController,
    profileInfo: AdvertiserProfileInfo
) {
    // ------------🔃 status ------------
    val adMode by viewModel.adMode.collectAsState()

    // ----------- 🚀 api value -----------
    val baseUrl = "http://192.168.247.89:8080/open/advertiser/image/profile"
    val bgUrl = "$baseUrl/${profileInfo.backgroundUnifiedCode}"
    val profileUrl = "$baseUrl/${profileInfo.profileUnifiedCode}"
    val livePackages by viewModel.livePackages.collectAsState()
    val expiredPackages by viewModel.expiredPackages.collectAsState()

    val displayedPackages = when (adMode) {
        AdvertiserProfileAdMode.LIVE    -> livePackages
        AdvertiserProfileAdMode.EXPIRED -> expiredPackages
    }

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(Unit) {
        viewModel.updateProfileInfo(profileInfo)
    }

    val coroutineScope = rememberCoroutineScope()

    // ----------- 🖼️ uI ---------------
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

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Advertiser background image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileUrl)
                .crossfade(true)
                .build(), // Replace with your image
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

        Button(
            onClick = { /* follow */ },
            modifier = Modifier
                .width(84.dp)
                .height(48.dp)
                .align(Alignment.TopEnd)
                .offset {
                    IntOffset(
                        x = with(density) { (-16).dp.toPx() }.toInt(),
                        y = with(density) { (animatedHeight + 10.dp).toPx() }.toInt()
                    )
                },
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "구독",
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    top = animatedHeight + 60.dp / 2 + 30.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { // user info box
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(250.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "@${profileInfo.advertiserLoginId}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = profileInfo.companyName,
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text(
                            text = profileInfo.serviceInfo,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = profileInfo.locationBrief,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = profileInfo.email,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = "5000+ followers",
                            style = MaterialTheme.typography.titleLarge,
                            color = SeaGreen
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = profileInfo.introduction ?: "",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
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
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.fetchLiveAdvertisementPackages()
                                    }
                                    viewModel.updateAdMode(AdvertiserProfileAdMode.LIVE)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "진행중인 광고",
                                )
                            }

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.fetchExpiredAdvertisementPackages()
                                    }
                                    viewModel.updateAdMode(AdvertiserProfileAdMode.EXPIRED)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "완료된 광고"
                                )
                            }
                        }
                    }
                }
            }

            items(displayedPackages.chunked(2)) { pkgs ->
                // advertisement...
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 200.dp)
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                     pkgs.forEach { pkg ->
                         val thumbItem = AdvertisementThumbnailItem.of(
                             generalFields = pkg.advertisementGeneralFields,
                             unifiedCode =
                             pkg.advertisementGeneralFields
                                 .thumbnailUri?.substringAfterLast('/')
                         )
                         VerticalAdvertisementThumbnail(
                             item = thumbItem,
                             onClick = { selectedThumb ->
                                 navController.navigate(
                                     ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                             "/${selectedThumb.advertisementId}"
                                 )
                             },
                             onToggleFavorite = {}
                         )
                     }
                    // Fill empty space if only 1 item in last row
                    if (pkgs.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

    }
}