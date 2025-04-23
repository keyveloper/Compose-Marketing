package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.enums.ReviewType
import com.example.marketing.viewmodel.AdvertisementDetailViewModel

@Composable
fun AdvertisementDetailScreen(
    advertisementViewModel: AdvertisementDetailViewModel = hiltViewModel(),
    id: Long
) {
    // Screen 시작시
    LaunchedEffect(id) {
        advertisementViewModel.init(id)
    }

    val stringBuilder = StringBuilder()

    val title by advertisementViewModel.title.collectAsState()
    val reviewType by advertisementViewModel.reviewType.collectAsState()
    val channelType by advertisementViewModel.channelType.collectAsState()
    val recruitmentNumber by advertisementViewModel.recruitmentNumber.collectAsState()
    val itemName by advertisementViewModel.itemName.collectAsState()
    val recruitmentStartAt by advertisementViewModel.recruitmentStartAt.collectAsState()
    val recruitmentEndAt by advertisementViewModel.recruitmentEndAt.collectAsState()
    val announcementAt by advertisementViewModel.announcementAt.collectAsState()
    val reviewStartAt by advertisementViewModel.reviewStartAt.collectAsState()
    val reviewEndAt by advertisementViewModel.reviewEndAt.collectAsState()
    val endAt by advertisementViewModel.endAt.collectAsState()
    val siteUrl by advertisementViewModel.siteUrl.collectAsState()
    val itemInfo by advertisementViewModel.itemInfo.collectAsState()
    val createdAt by advertisementViewModel.createdAt.collectAsState()
    val updatedAt by advertisementViewModel.updatedAt.collectAsState()
    val city by advertisementViewModel.city.collectAsState()
    val district by advertisementViewModel.district.collectAsState()
    val scrollState = rememberScrollState()

    val channelIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = ChannelIcon.fromCode(channelType.code)!!.painterId),
            contentDescription = "Channel type icon"
        )
    }

    val reviewIcon: @Composable () -> Unit = {
        Icon(
            imageVector = ReviewIcon.fromCode(reviewType.code)!!.iconVector ,
            contentDescription = "review type icon"
        )
        Spacer(Modifier.width(10.dp))
        Text("Review Type")
    }

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
                    model = siteUrl ?: "",
                    contentDescription = "Item Top Image",
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
                text = title.ifEmpty { "Advertisement Title" },
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // (3) Type Infos with icons
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                channelIcon()
                Spacer(Modifier.width(10.dp))
                Text(channelType.name)

                Spacer(Modifier.width(10.dp))

                reviewIcon()
                Spacer(Modifier.width(10.dp))
                Text(reviewType.name)
            }

            Spacer(modifier = Modifier.height(16.dp))

            //  (4) item info box
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
                        Text("[제공 정보]")
                    }
                    Text("[${itemName}]")
                    Text(itemInfo?: "")
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
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    Text("모집 인원", style = MaterialTheme.typography.labelSmall)
                    Text(stringBuilder.append(recruitmentNumber).append(" 명").toString())

                    Text("모집 기간", style = MaterialTheme.typography.labelSmall)
                    Text(
                        stringBuilder
                            .append(recruitmentStartAt)
                            .append("~")
                            .append(recruitmentEndAt)
                            .toString())

                    Text("리뷰어 발표", style = MaterialTheme.typography.labelSmall)
                    Text(announcementAt)

                    Text("리뷰 등록 기간", style = MaterialTheme.typography.labelSmall)
                    Text(
                        stringBuilder
                            .append(reviewStartAt)
                            .append("~")
                            .append(reviewEndAt)
                            .toString()
                    )

                    Text("캠페인 마감", style = MaterialTheme.typography.labelSmall)
                    Text(endAt)
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
    AdvertisementDetailScreen(
        id = 1L
    )
}