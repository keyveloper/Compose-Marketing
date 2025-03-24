package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.component.AdvertisementThumbnail
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.MainBottomBar
import com.example.marketing.ui.component.MainTopBar
import com.example.marketing.viewmodel.MainViewModel

@Composable
fun AdvertisementDetailScreen() {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        color = Color.White

    ) {
        Column(
        ) {
            // header: image box
            Box(modifier = Modifier.fillMaxWidth()) {
                // 배경 이미지
                AsyncImage(
                    model = "https://example.com/food_image.jpg",
                    contentDescription = "Top Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        .background(MaterialTheme.colorScheme.outline)
                    , // 예시 높이
                    contentScale = ContentScale.Crop
                )
                // 왼쪽 상단 뒤로가기 아이콘, 오른쪽 상단 시간 표시
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp , vertical = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* 뒤로가기 */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    IconButton(onClick = { /* 찜하기 */ }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "찜하기 이이콘"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // adjust content padding
            Surface(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

            }
            // (2) 제목 (Advertisement Title)
            Text(
                text = "Advertisement Title",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Type Infos with icons
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = "Channel type icon"
                )
                Spacer(Modifier.width(10.dp))
                Text("Channel Type")

                Spacer(Modifier.width(10.dp))

                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "review type icon"
                )
                Spacer(Modifier.width(10.dp))
                Text("Review Type")
            }

            Spacer(modifier = Modifier.height(16.dp))

            //  item info box
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(3.dp) // 모든 모서리 8.dp 둥글게 처리
                    )
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "item info icon",
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text("item info")
                    }

                    Text("Item Info hrerereer")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Advertisement Info
            Text(
                text = "Advertisement Info",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(375.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(3.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    Text("모집 인원")
                    Text("모집 기간")

                    Text("리뷰어 발표")

                    Text("리뷰 등록 기간")

                    Text("캠페인 마감")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // keyword Box
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(3.dp)
                        )
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "추천 키워드",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "#keyword here!!",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text("#??")
                        Text("#dfaf")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // submit Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,  // 배경색
                    contentColor = Color.Gray   // 버튼 내부 텍스트 및 아이콘 색상
                ),
                shape = RoundedCornerShape(
                    topStart = 3.dp,
                    topEnd = 3.dp,
                    bottomEnd = 3.dp,
                    bottomStart = 3.dp
                )
            ) {
                Text("신청하기")
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdvertisementDetailScreen() {
    AdvertisementDetailScreen()
}