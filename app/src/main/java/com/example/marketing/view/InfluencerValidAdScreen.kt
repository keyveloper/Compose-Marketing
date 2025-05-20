package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.enums.MyOfferStatus
import com.example.marketing.enums.ReviewOfferStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.ui.color.ApricotCream
import com.example.marketing.viewmodel.InfluencerValidAdViewModel

@Composable
fun InfluencerValidAdScreen(
    viewModel: InfluencerValidAdViewModel = hiltViewModel(),
    navController: NavController
) {
    // ----------- 📌 fixed ---------
    val subTabs = listOf(
        MyOfferStatus.OFFERING,
        MyOfferStatus.PROGRESSING,
        MyOfferStatus.COMPLETED
    )

    // ------------🔃 status ------------
    var selectedSubTabIdx by remember { mutableStateOf(0) }
    val myOfferStatus by viewModel.myOfferStatus.collectAsState()

    // ----------- 🚀 api value -----------
    val validOffers by viewModel.validOffers.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(Unit) {
        viewModel.fetchValliOfferAll()
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(ApricotCream),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedSubTabIdx,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                // Draw the indicator under the selected tab
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSubTabIdx]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 3.dp
                )
            }
        ) {
            subTabs.forEachIndexed { index, status ->
                Tab(
                    selected = selectedSubTabIdx == index,
                    onClick = {
                        selectedSubTabIdx = index
                        viewModel.updateMyOfferStatus(status)
                    },
                    text = { Text(status.title) }
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
        ) {
            when(myOfferStatus) {
                MyOfferStatus.OFFERING -> {
                    val filteredOffers = validOffers.filter {
                        it.offerStatus == ReviewOfferStatus.OFFER
                    }
                    items(filteredOffers.size) { index ->

                        val offerInfo = validOffers[index]
                        val uuid = offerInfo.thumbnailUrl?.substringAfterLast('/')

                        if (uuid != null) {
                            val url = "http://192.168.100.89:8080/open/advertisement/image/${uuid}"
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
                                                "/${offerInfo.advertisementId}"
                                        )
                                    },
                                contentScale   = ContentScale.Crop
                            )
                        }
                    }
                }

                MyOfferStatus.PROGRESSING -> {
                    val filteredOffers = validOffers.filter {
                        it.offerStatus == ReviewOfferStatus.OFFER
                    }
                    items(filteredOffers.size) { index ->

                        val offerInfo = validOffers[index]
                        val uuid = offerInfo.thumbnailUrl?.substringAfterLast('/')

                        if (uuid != null) {
                            val url = "http://192.168.100.89:8080/open/advertisement/image/${uuid}"
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Advertisement Image",
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clickable {  },
                                contentScale   = ContentScale.Crop
                            )
                        }
                    }
                }

                MyOfferStatus.COMPLETED -> {
                    val filteredOffers = validOffers.filter {
                        it.offerStatus == ReviewOfferStatus.OFFER
                    }
                    items(filteredOffers.size) { index ->

                        val offerInfo = validOffers[index]
                        val uuid = offerInfo.thumbnailUrl?.substringAfterLast('/')

                        if (uuid != null) {
                            val url = "http://192.168.100.89:8080/open/advertisement/image/${uuid}"
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Advertisement Image",
                                modifier = Modifier.aspectRatio(1f),
                                contentScale   = ContentScale.Crop
                            )
                        }
                    }
                }
            }

        }
    }
}