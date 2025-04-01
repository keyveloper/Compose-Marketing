package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.VerticalAdvertisementThumbnail
import com.example.marketing.ui.widget.CategoryBox
import com.example.marketing.viewmodel.EventViewModel

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel = hiltViewModel()
) {
    // 예시 데이터 (실제 데이터는 API 등을 통해 받아오기)
    val advertisementItems = (1..30).map { i ->
        AdvertisementThumbnailItem(
            advertisementId = i.toLong(),
            thumbnailImageUrl = "",
            title = "Advertisement Title $i",
            itemName = "Item Name $i",
            channelType = ChannelType.BLOGGER,
            reviewType = ReviewType.VISITED
        )
    }
    // LazyColumn의 스크롤 상태를 이용하여 오프셋 값을 가져옴
    val listState = rememberLazyListState()
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        eventViewModel.fetchFresh()
    }

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
                            eventViewModel.changeEventStatus(status)
                        }
                    )
                    CategoryBox(
                        emoji = "☠️",
                        eventCode = 2,
                        icon = {},
                        onCategorySelected = { status ->
                            eventViewModel.changeEventStatus(status)
                        }
                    )
                    CategoryBox(
                        emoji = "🍎",
                        eventCode = 0,
                        icon = {},
                        onCategorySelected = { status ->
                            eventViewModel.changeEventStatus(status)
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
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Timeline State Here",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // 광고 아이템들을 두 그룹(왼쪽, 오른쪽)으로 나눔.
        // 오른쪽은 스크롤 오프셋에 반대 방향의 offset을 적용해 움직임을 반전.
        val leftItems = advertisementItems.take(advertisementItems.size / 2 - 1)
        val rightItems = advertisementItems.drop(advertisementItems.size / 2)
        val rowCount = maxOf(leftItems.size, rightItems.size)

        items(rowCount) { rowIndex ->
            // 전체 LazyColumn의 스크롤 오프셋을 가져와서 우측 컬럼에 적용할 offset으로 변환.
            // (여기서는 첫 번째 보이는 아이템의 오프셋만 사용합니다)
            val scrollOffsetPx = listState.firstVisibleItemScrollOffset
            val rightOffsetDp = with(density) { (-scrollOffsetPx).toDp() }

            Row(modifier = Modifier.fillMaxWidth()) {
                // 왼쪽 박스: 스크롤 오프셋 없이 그대로 표시
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    if (rowIndex < leftItems.size) {
                        VerticalAdvertisementThumbnail(
                            item = leftItems[rowIndex],
                            modifier = Modifier.fillMaxWidth()
                        ) { }
                    }
                }
                // 오른쪽 박스: 스크롤 오프셋의 음수 값을 offset으로 적용해 반대 방향으로 움직임
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .offset(y = rightOffsetDp)
                ) {
                    if (rowIndex < rightItems.size) {
                        VerticalAdvertisementThumbnail(
                            item = rightItems[rowIndex],
                            modifier = Modifier.fillMaxWidth()
                        ) { }
                    }
                }
            }
        }
    }
}
