package com.example.marketing.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.color.LavenderPurple
import com.example.marketing.ui.color.PersianPastel

@Composable
fun AdvertisementWritingScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
            .background(PersianPastel),
        verticalArrangement = Arrangement.spacedBy(24.dp),
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
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("Title") },
                )

                // Dropdown menu - channel, review
                var channelExpended by remember { mutableStateOf(false) }
                val channels: List<ChannelType> = listOf(
                    ChannelType.BLOGGER,
                    ChannelType.INSTAGRAM,
                    ChannelType.YOUTUBER,
                    ChannelType.THREAD
                )
                var selectedChannel by remember { mutableStateOf(channels[0]) }

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
                var selectedReviews by remember { mutableStateOf(reviews[0]) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        OutlinedButton(
                            onClick = { channelExpended = true }
                        ) {
                            Text("select channel")
                        }

                        DropdownMenu(
                            expanded = channelExpended,
                            onDismissRequest = { channelExpended = false }
                        ) {
                            channels.forEach { channel ->
                                DropdownMenuItem(
                                    text = { Text(channel.name) },
                                    onClick = {
                                        selectedChannel = ChannelType.valueOf(channel.name)
                                        channelExpended = false
                                    },
                                    leadingIcon = {
                                        val channelIcon = ChannelIcon.fromCode(channel.code)
                                        Icon(
                                            painter = painterResource(channelIcon!!.painterId),
                                            contentDescription = channelIcon.description
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Column {
                        OutlinedButton(
                            onClick = { reviewExpended = true }
                        ) {
                            Text("select review type")
                        }

                        DropdownMenu(
                            expanded = reviewExpended,
                            onDismissRequest = { reviewExpended = false }
                        ) {
                            reviews.forEach { review ->
                                DropdownMenuItem(
                                    text = { Text(review.name) },
                                    onClick = {
                                        selectedChannel = ChannelType.valueOf(review.name)
                                        channelExpended = false
                                    },
                                    leadingIcon = {
                                        val reviewIcon = ReviewIcon.fromCode(review.code)!!
                                        Icon(
                                            imageVector = reviewIcon.iconVector,
                                            contentDescription = reviewIcon.description
                                        )
                                    }
                                )
                            }
                        }
                    }
                }


                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("Item name") },
                )

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("Item detail Info(Optional)") },
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
                var showSingleDatePicker by remember { mutableStateOf(false) }
                var showRangeDatePicker by remember { mutableStateOf(false) }
                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("모집 인원") },
                )

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("모집 기간") },
                )

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("리뷰어 발표 일자") },
                )

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("컨텐츠 등록기간") },
                )

                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text("캠파인 종료") },
                )
            }
        }

        // keyword
    }
}