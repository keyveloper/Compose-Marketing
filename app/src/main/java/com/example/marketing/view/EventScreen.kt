package com.example.marketing.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.EventStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType
import com.example.marketing.ui.color.MintCream
import com.example.marketing.ui.component.VerticalAdvertisementThumbnail
import com.example.marketing.ui.widget.CategoryBox
import com.example.marketing.viewmodel.EventViewModel

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = hiltViewModel(),
    navController: NavController,
    userId: Long,
    userType: UserType
) {
    // ------------🔃 status ------------
    val eventStatus by viewModel.eventStatus.collectAsState()
    val totalApiCallStatus by viewModel.totalApiCategory.collectAsState()

    // ----------- 🚀 from server value -----------
    val thumbnailItem by viewModel.thumbnailItem.collectAsState()
    val packages by viewModel.packages.collectAsState()

    // ----------- 🎮 controller -------------
    LaunchedEffect(userId) {
        viewModel.updateUserId(userId)
        viewModel.updateUserType(userType)
    }

    LaunchedEffect(eventStatus) {
        Log.i("eventScreen", "eventStatus $eventStatus")
        when (eventStatus) {
            EventStatus.IDLE -> {
                viewModel.testFetch()
            }

            EventStatus.FRESH-> {
                viewModel.fetchFreshWithThumbnail()
            }

            EventStatus.DEADLINE -> {
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
                        eventStatus = EventStatus.HOT,
                        icon = {},
                        onCategorySelected = { status ->
                            viewModel.updateEventStatus(status)
                        }
                    )
                    CategoryBox(
                        emoji = "☠️",
                        eventStatus = EventStatus.DEADLINE,
                        icon = {},
                        onCategorySelected = { status ->
                            viewModel.updateEventStatus(status)
                        }
                    )
                    CategoryBox(
                        emoji = "🍎",
                        eventStatus = EventStatus.FRESH,
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

            when(eventStatus) {
                EventStatus.FRESH -> {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "😎 오늘 올라온 광고를 확인하세요",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                EventStatus.DEADLINE -> {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "⌛ 이런...! 모집 시간이 얼마 남지 않았어요",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                EventStatus.HOT -> {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "🔥 요즘은 이런 광고가 인기있어요",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                else -> {

                }
            }


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
                    if (userType == UserType.INFLUENCER) {
                        VerticalAdvertisementThumbnail(
                            item = thumb,
                            onClick = { selectedThumb ->
                                navController.navigate(
                                    ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                            "/${selectedThumb.advertisementId}"
                                )
                            },
                            onToggleFavorite = {
                                viewModel.favorite(advertisementId = thumb.advertisementId)
                            },

                        )
                    } else {
                        VerticalAdvertisementThumbnail(
                            item = thumb,
                            onClick = { selectedThumb ->
                                navController.navigate(
                                    ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                            "/${selectedThumb.advertisementId}"
                                )
                            },
                            onToggleFavorite = {

                            }
                        )
                    }

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
