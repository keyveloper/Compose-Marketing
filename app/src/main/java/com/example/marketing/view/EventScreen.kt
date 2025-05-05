package com.example.marketing.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.EventStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.ui.color.MintCream
import com.example.marketing.ui.component.VerticalAdvertisementThumbnail
import com.example.marketing.ui.widget.CategoryBox
import com.example.marketing.viewmodel.EventViewModel

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = hiltViewModel(),
    navController: NavController
) {
    // ------------🔃 status ------------
    val eventStatus by viewModel.eventStatus.collectAsState()
    val totalApiCallStatus by viewModel.totalApiCategory.collectAsState()

    // ----------- 🚀 from server value -----------
    val thumbnailItem by viewModel.thumbnailItem.collectAsState()
    val packages by viewModel.packages.collectAsState()

    // ----------- 🎮 controller -------------
    LaunchedEffect(eventStatus) {
        when (eventStatus) {
            EventStatus.IDLE -> {
                viewModel.testFetch()
            }

            EventStatus.FRESH-> {
                viewModel.clearItems()
                viewModel.fetchFreshWithThumbnail()
            }

            EventStatus.DEADLINE -> {
                viewModel.clearItems()
                viewModel.fetchDeadlineWithThumbnail()
            }

            else -> {

            }
        }
        // eventViewModel.fetchFresh()
    }

    // ------------ 🖼️ UI ------------------
    // LazyColumn의 스크롤 상태를 이용하여 오프셋 값을 가져옴
    val listState = rememberLazyListState()
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = totalApiCallStatus == ApiCallStatus.SUCCESS,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) { }
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        // 헤더 영역
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    CategoryBox(
                        emoji = "🔥",
                        eventCode = 1,
                        icon = {},
                        onCategorySelected = { status ->
                            viewModel.updateEventStatus(status)
                        }
                    )
                    CategoryBox(
                        emoji = "☠️",
                        eventCode = 2,
                        icon = {},
                        onCategorySelected = { status ->
                            viewModel.updateEventStatus(status)
                        }
                    )
                    CategoryBox(
                        emoji = "🍎",
                        eventCode = 0,
                        icon = {},
                        onCategorySelected = { status ->
                            viewModel.updateEventStatus(status)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
            }
        }

        // 상태 텍스트 등 추가 헤더 요소
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Timeline State Here",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(thumbnailItem.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach { thumb ->
                    VerticalAdvertisementThumbnail(
                        item = thumb,
                        onClick = { selectedThumb ->
                            val adPackage = packages.firstOrNull {
                                it.advertisementGeneralFields.id ==
                                        selectedThumb.advertisementId
                            } ?: return@VerticalAdvertisementThumbnail

                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("adPackage", adPackage)
                            navController.navigate(ScreenRoute.MAIN_HOME_AD_DETAIL.route)
                        }
                    )
                }

                // Fill empty space if only 1 item in last row
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

    AnimatedVisibility(
        visible = totalApiCallStatus != ApiCallStatus.SUCCESS,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = 4.dp,
                color = MintCream
            )
        }
    }
}
