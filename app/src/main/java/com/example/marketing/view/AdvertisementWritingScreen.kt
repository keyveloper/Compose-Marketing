package com.example.marketing.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.color.LavenderPurple
import com.example.marketing.ui.color.PersianPastel
import com.example.marketing.ui.component.RangeDatePicker
import com.example.marketing.ui.component.SingleDatePicker
import com.example.marketing.viewmodel.AdvertisementWritingViewModel

@Composable
fun AdvertisementWritingScreen(
    modifier: Modifier = Modifier,
    viewModel: AdvertisementWritingViewModel = hiltViewModel(),
) {
    val title = viewModel.title.collectAsState()
    val selectedChannelType = viewModel.channelType.collectAsState()
    val selectedReviewType = viewModel.reviewType.collectAsState()
    val itemName = viewModel.itemName.collectAsState()
    val itemInfo = viewModel.itemInfo.collectAsState()
    val recruitmentNumber = viewModel.recruitmentNumber.collectAsState()
    val recruitStartAt = viewModel.recruitStartAt.collectAsState()
    val recruitEndAt = viewModel.recruitEndAt.collectAsState()
    val announcementAt = viewModel.announcementAt.collectAsState()
    val reviewStartAt = viewModel.reviewStartAt.collectAsState()
    val reviewEndAt = viewModel.reviewEndAt.collectAsState()
    val siteUrl = viewModel.siteUrl.collectAsState()

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
            .background(PersianPastel),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // # image Box
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrls = listOf("1", "2", "3", "4", "5")
            val pagerState = rememberPagerState(
                pageCount = { imageUrls.size }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(vertical = 12.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { currentPage ->
                        Log.i("advertisementWriting", "currentPage: $currentPage")

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Card(
                                modifier.fillMaxSize(),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                AsyncImage(
                                    model = imageUrls[currentPage],
                                    contentDescription = "Advertisement Image added",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(
                                            RoundedCornerShape(
                                                bottomStart = 16.dp, bottomEnd = 16.dp))
                                        .background(MaterialTheme.colorScheme.outline),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp , vertical = 25.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* 사진 더하기 로직 */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "Add new Image"
                            )
                        }
                    }
                }
            }
        }

        // # Title & Details
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(450.dp)
                .background(LavenderPurple)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title.value,
                    onValueChange = {
                        viewModel.updateTitle(it)
                    },
                    label = { Text("제목") },
                )

                // Dropdown menu - channel, review
                var channelExpended by remember { mutableStateOf(false) }
                val channels: List<ChannelType> = listOf(
                    ChannelType.BLOGGER,
                    ChannelType.INSTAGRAM,
                    ChannelType.YOUTUBER,
                    ChannelType.THREAD
                )

                var channelIcon by remember {
                    mutableStateOf(ChannelIcon.fromCode(selectedChannelType.value.code)!! )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { channelExpended = true }
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                painter = painterResource(channelIcon.painterId),
                                contentDescription = channelIcon.description,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                            Text(text = selectedChannelType.value.name)
                        }

                        DropdownMenu(
                            expanded = channelExpended,
                            onDismissRequest = { channelExpended = false }
                        ) {
                            channels.forEach { channel ->
                                DropdownMenuItem(
                                    text = { Text(channel.name) },
                                    onClick = {
                                        viewModel.updateChannelType(
                                            ChannelType.valueOf(channel.name))
                                        channelIcon = ChannelIcon.fromCode(channel.code)!!
                                        channelExpended = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(
                                                ChannelIcon.fromCode(channel.code)!!.painterId
                                            ),
                                            contentDescription = channelIcon.description,
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    var reviewExpended by remember { mutableStateOf(false) }
                    val reviews: List<ReviewType> = listOf(
                        ReviewType.BUY,
                        ReviewType.ARTICLE,
                        ReviewType.VISITED,
                        ReviewType.RECEIPT,
                        ReviewType.DELIVERY,
                        ReviewType.LONG_FORM,
                        ReviewType.SHORT_FROM
                    )

                    var reviewIcon by remember {
                        mutableStateOf(ReviewIcon.fromCode(selectedReviewType.value.code)!! )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { reviewExpended = true }
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = reviewIcon.iconVector,
                                contentDescription = reviewIcon.description,
                            )
                            Text(text = selectedReviewType.value.name)
                        }

                        DropdownMenu(
                            expanded = reviewExpended,
                            onDismissRequest = { reviewExpended = false }
                        ) {
                            reviews.forEach { review ->
                                DropdownMenuItem(
                                    text = { Text(review.name) },
                                    onClick = {
                                        viewModel.updateReviewType(
                                            ReviewType.valueOf(review.name)
                                        )
                                        reviewIcon = ReviewIcon.fromCode(review.code)!!
                                        reviewExpended = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = ReviewIcon
                                                .fromCode(review.code)!!.iconVector,
                                            contentDescription = reviewIcon.description
                                        )
                                    }
                                )
                            }
                        }
                    }
                }


                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = itemName.value,
                    onValueChange = { viewModel.updateItemName(it) },
                    label = { Text("제품 이름") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp),
                    value = itemInfo.value ?: "",
                    onValueChange = { viewModel.updateItemInfo(it) },
                    label = { Text("제품 소개(Optional)") },
                )
            }
        }

        // # apply  info
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(800.dp)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = { },
                    label = { Text("모집 인원") },
                )

                RangeDatePicker(
                    label = "모집기간",
                    modifier = Modifier.fillMaxWidth()
                )

                SingleDatePicker(
                    label = "리뷰어 발표일",
                    modifier = Modifier.fillMaxWidth()
                )

                RangeDatePicker(
                    label = "컨텐츠 등록기간",
                    modifier = Modifier.fillMaxWidth()
                )

                SingleDatePicker(
                    label = "캠파인 종료",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // keyword
    }
}