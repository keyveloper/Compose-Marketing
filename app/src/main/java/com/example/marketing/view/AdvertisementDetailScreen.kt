package com.example.marketing.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.ui.color.MintCream
import com.example.marketing.viewmodel.AdvertisementDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementDetailScreen(
    viewModel: AdvertisementDetailViewModel = hiltViewModel(),
    advertisementId: Long,
) {
    // ------------✍️ input value -------------
    val inputOffer by viewModel.inputOffer.collectAsState()

    // ----------- 🚀 API data  ----------
    val bytes by viewModel.imageBytesList.collectAsState()
    val pkg by viewModel.advertisementPackage.collectAsState()
    val influencerInfos by viewModel.influencerInfo.collectAsState()

    // ----------- 😏 utils  ----------
    val stringBuilder = StringBuilder()

    // ----------- 🎮 controller  ----------
    LaunchedEffect(advertisementId) {
        viewModel.updateAdvertisementId(advertisementId)
        viewModel.fetchDetailAndImages()
    }


    // ----------- 🔃 status  ----------
    val scrollState = rememberScrollState()
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showDialog by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = pkg != null,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        val generalField = pkg!!.advertisementGeneralFields

        val channelIcon: @Composable () -> Unit = {
            Icon(
                painter = painterResource(id = ChannelIcon.fromCode(
                    generalField.channelType.code)!!.painterId
                ),
                contentDescription = "Channel type icon",
                tint = Color.Unspecified
            )
        }

        val reviewIcon: @Composable () -> Unit = {
            Icon(
                imageVector = ReviewIcon.fromCode(
                    generalField.reviewType.code)!!.iconVector ,
                contentDescription = "review type icon"
            )
            Spacer(Modifier.width(10.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 56.dp)
                .background(Color.White)
            ,
        ) {
            Column {
                // header: image box
                Box(modifier = Modifier.fillMaxWidth()) {
                    val pagerState = rememberPagerState(
                        pageCount = { bytes.size.coerceAtLeast(1) }
                    )

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(MintCream)
                    ) { currentPage ->
                        val currentByte = bytes.getOrNull(currentPage)

                        AsyncImage(
                            model = currentByte,
                            contentDescription = "Item Top Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                                .background(MaterialTheme.colorScheme.outline)
                            , // 예시 높이
                            contentScale = ContentScale.Crop
                        )
                    }

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
                
                // (2) 제목 (Advertisement Title)
                Text(
                    text = generalField.title.ifEmpty { "Advertisement Title" },
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
                    Text(generalField.channelType.name)

                    Spacer(Modifier.width(10.dp))

                    reviewIcon()
                    Spacer(Modifier.width(10.dp))
                    Text(generalField.reviewType.name)
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
                        Text("[${generalField.itemName}]")
                        Text(generalField.itemInfo?: "")
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
                        Text(stringBuilder.append(
                            generalField.recruitmentNumber).append(" 명").toString())

                        Text("모집 기간", style = MaterialTheme.typography.labelSmall)
                        Text(
                            stringBuilder
                                .append(generalField.recruitmentStartAt)
                                .append("~")
                                .append(generalField.recruitmentEndAt)
                                .toString())

                        Text("리뷰어 발표", style = MaterialTheme.typography.labelSmall)
                        Text(text = generalField.announcementAt.toString())


                        Text("리뷰 등록 기간", style = MaterialTheme.typography.labelSmall)
                        Text(
                            stringBuilder
                                .append(generalField.reviewStartAt)
                                .append("~")
                                .append(generalField.reviewEndAt)
                                .toString()
                        )

                        Text("캠페인 마감", style = MaterialTheme.typography.labelSmall)
                        Text(generalField.endAt.toString())

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


            }

            // review offer button
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        top = 16.dp,
                        bottom = 82.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .size(56.dp),  // standard circular FAB size
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "review offer",
                    tint = Color.White
                )
            }

            // fetch reviewOffers button
            FloatingActionButton(
                onClick = {
                    viewModel.fetchOffers()
                    showSheet = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(56.dp),  // standard circular FAB size
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Comment,
                    contentDescription = "View Offers",
                    tint = Color.White
                )
            }

            // dialog for sending offer
            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Column (
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "😏 매력적인 제안을 해보세요",
                                style = MaterialTheme.typography.titleMedium
                            )

                            OutlinedTextField(                 // 2️⃣ decorate only
                                value = inputOffer,
                                onValueChange = {
                                    viewModel.updateInputOffer(it)
                                },            // no typing
                                label = { Text("🫱 제안하기") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedButton(
                                enabled = inputOffer.isNotEmpty(),
                                modifier = Modifier.fillMaxSize(),
                                onClick = {
                                    viewModel.offer()
                                    showDialog = false
                                    viewModel.updateInputOffer("")
                                },
                                shape = RectangleShape
                            ) { Text("제안하기") }
                        }
                    }
                }
            }

            // Bottom sheet for viewing offers
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "Review Offers",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.height(8.dp))

                        LazyColumn {
                            items(influencerInfos.size) { idx ->
                                Text(influencerInfos[idx].influencerLoginId)
                                Text(influencerInfos[idx].offer)
                            }
                        }
                    }
                }
            }
        }
    }

}
