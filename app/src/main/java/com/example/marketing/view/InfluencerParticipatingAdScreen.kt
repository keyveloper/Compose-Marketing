package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.domain.AdParticipatedByInfluencer
import com.example.marketing.enums.InfluencerParticipatingViewTabState
import com.example.marketing.enums.ReviewOfferStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.state.InfluencerParticipatingAdInitState
import com.example.marketing.ui.color.ClassicBlue
import com.example.marketing.ui.color.SunnyAmber
import com.example.marketing.viewmodel.InfluencerParticipatingAdViewModel

@Composable
fun InfluencerParticipatingAdScreen(
    viewModel: InfluencerParticipatingAdViewModel = hiltViewModel(),
    navController: NavController
) {
    // ----------- 📌 fixed ---------
    val subTabs = listOf(
        InfluencerParticipatingViewTabState.OFFERING,
        InfluencerParticipatingViewTabState.PROGRESSING,
        InfluencerParticipatingViewTabState.COMPLETED
    )

    // ------------🔃 status ------------
    var selectedSubTabIdx by remember { mutableStateOf(0) }
    val tabState by
        viewModel.influencerParticipatingViewTabState.collectAsState()
    val initState by viewModel.initState.collectAsStateWithLifecycle()

    // ----------- 🔭 Launched Effect -------------
    when(initState) {
        is InfluencerParticipatingAdInitState.Loading -> { CommonLoadingView() }
        is InfluencerParticipatingAdInitState.Error -> { CommonErrorView(
            message = (initState as InfluencerParticipatingAdInitState.Error).msg,
            onRetry = { viewModel.refresh() })
        }
        is InfluencerParticipatingAdInitState.Ready -> {
            val totalFridItems = (initState
                    as InfluencerParticipatingAdInitState.Ready).adParticipatedByInfluencer
            val filteredItems = remember(tabState, initState) {
                when (tabState) {
                    InfluencerParticipatingViewTabState.OFFERING -> {
                        totalFridItems.filter { it.offerStatus == ReviewOfferStatus.OFFER }
                    }
                    InfluencerParticipatingViewTabState.PROGRESSING -> {
                        totalFridItems.filter { it.offerStatus == ReviewOfferStatus.PROGRESSING }
                    }
                    InfluencerParticipatingViewTabState.COMPLETED -> {
                        totalFridItems.filter { it.offerStatus == ReviewOfferStatus.COMPLETED }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedSubTabIdx,
                    modifier = Modifier.fillMaxWidth(),
                    contentColor = ClassicBlue,
                    containerColor = Color.White,
                    indicator = { tabPositions ->
                        // Draw the indicator under the selected tab
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSubTabIdx]),
                            color = SunnyAmber,
                            height = 3.dp
                        )
                    }
                ) {
                    subTabs.forEachIndexed { index, state ->
                        Tab(
                            selected = selectedSubTabIdx == index,
                            onClick = {
                                selectedSubTabIdx = index
                                viewModel.updateInfluencerOwnedTabStatus(state)
                            },
                            text = { Text(state.title) }
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(filteredItems) { item ->

                        val uuid = item.thumbnailUrl?.substringAfterLast('/')

                        if (uuid != null) {
                            val url = "http://192.168.100.89:8080/open/advertisement/image/${uuid}"

                            if (item.offerStatus == ReviewOfferStatus.COMPLETED) {
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .padding(0.5.dp)
                                        .clickable {
                                            navController.navigate(
                                                ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                                        "/${item.advertisementId}/INFLUENCER"
                                            )
                                        }
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(url)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Advertisement Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    // 🔲 Black overlay
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black.copy(alpha = 0.5f))  // adjust alpha as needed
                                    )
                                }
                            } else {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Advertisement Image",
                                    modifier = Modifier.aspectRatio(1f)
                                        .padding(0.5.dp)
                                        .clickable {
                                            navController.navigate(
                                                ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                                        "/${item.advertisementId}/INFLUENCER"
                                            )
                                        },
                                    contentScale   = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}