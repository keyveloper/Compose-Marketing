package com.example.marketing.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType
import com.example.marketing.state.AdDetailInitFetchedState
import com.example.marketing.ui.color.MintCream
import com.example.marketing.viewmodel.AdvertisementDetailViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementDetailScreen(
    viewModel: AdvertisementDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    // ----------- ⛏️ init value -------------
    val userType by viewModel.userType.collectAsState()

    // ------------✍️ input value -------------
    val inputOffer by viewModel.inputOffer.collectAsState()

    // ----------- 🚀 API data  ----------
    val state by viewModel.initFetchedState.collectAsStateWithLifecycle()
    val influencerInfos by viewModel.influencerInfos.collectAsStateWithLifecycle()

    // ----------- 😏 utils  ----------

    // ----------- 🎮 controller  ----------

    // ----------- 🔨 tools ---------------

    when (state) {
        is AdDetailInitFetchedState.Loading -> CommonLoadingView()
        is AdDetailInitFetchedState.Error -> CommonErrorView(
            message = (state as AdDetailInitFetchedState.Error).msg,
            onRetry = { viewModel.refresh() }
        )

        is AdDetailInitFetchedState.Ready -> {
            Log.i("AdDetailScreen", "$userType")
            val scrollState = rememberScrollState()
            var showSheet by remember { mutableStateOf(false) }
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
            var showOfferDialog by remember { mutableStateOf(false) }
            val generalField =
                (state as AdDetailInitFetchedState.Ready).pkg.advertisementGeneralFields

            val channelIcon: @Composable () -> Unit = {
                Icon(
                    painter = painterResource(
                        id = ChannelIcon.fromCode(
                            generalField.channelType.code
                        )!!.painterId
                    ),
                    contentDescription = "Channel type icon",
                    tint = Color.Unspecified
                )
            }

            val reviewIcon: @Composable () -> Unit = {
                Icon(
                    imageVector = ReviewIcon.fromCode(
                        generalField.reviewType.code
                    )!!.iconVector,
                    contentDescription = "review type icon"
                )
                Spacer(Modifier.width(10.dp))
            }

            //
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(bottom = 56.dp)
                        .background(Color.White),
                ) {
                    Column {
                        // header: image box
                        Box(modifier = Modifier.fillMaxWidth()) {
                            val pagerState = rememberPagerState(
                                pageCount = { generalField.imageUris.size }
                            )

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .background(MintCream)
                            ) { currentPage ->
                                val currentImageUri = generalField.imageUris[currentPage]
                                val unifiedUri = currentImageUri?.substringAfterLast("/") ?: ""
                                val url =
                                    "http://192.168.100.89:8080/open/advertisement/image/$unifiedUri"

                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Advertisement Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .clip(
                                            RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                                        )
                                        .background(MaterialTheme.colorScheme.outline),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            // 왼쪽 상단 뒤로가기 아이콘, 오른쪽 상단 시간 표시
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 25.dp),
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

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    navController.navigate(
                                        ScreenRoute.MAIN_PROFILE_ADVERTISER.route +
                                        "/${generalField.advertiserId}/${userType}"
                                    )
                                },
                            text = "@${generalField.advertiserLoginId}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
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
                                .height(200.dp)
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
                                    Text(
                                        "[제공 정보]",
                                        color = Color(0xFF7a7a7a)
                                    )
                                }

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color(0xFF7a7a7a)
                                            )
                                        ) {
                                            append("[제품명]")
                                        }
                                        append("\n${generalField.itemName}")
                                    }
                                )

                                Spacer(Modifier.height(8.dp))
                                Text(
                                    buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color(0xFF7a7a7a)
                                            )
                                        ) {
                                            append("[제공 내역]")
                                        }
                                        append("\n${generalField.itemInfo ?: ""}")
                                    }
                                )
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
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .fillMaxSize(),
                            ) {
                                Text(
                                    "[모집 인원]",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF7a7a7a)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    buildAnnotatedString {
                                        append(generalField.recruitmentNumber.toString())
                                        append(" 명")
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(Modifier.height(16.dp))

                                Text(
                                    "[리뷰어 선정 기간]",
                                    style = MaterialTheme.typography.labelLarge ,
                                    color = Color(0xFF7a7a7a)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    buildAnnotatedString {
                                        val recruitStartAtToJava = Instant.fromEpochMilliseconds(
                                            generalField.recruitmentStartAt
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                            .toJavaLocalDateTime()

                                        val formattedStartAt = recruitStartAtToJava.format(
                                            DateTimeFormatter.ofPattern(
                                                "yy.MM.dd"
                                            )
                                        )

                                        val recruitmentEndAt = Instant.fromEpochMilliseconds(
                                            generalField.recruitmentEndAt
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                            .toJavaLocalDateTime()

                                        val formattedEndAt = recruitmentEndAt.format(
                                            DateTimeFormatter.ofPattern(
                                                "yy.MM.dd"
                                            )
                                        )

                                        append(formattedStartAt.toString())
                                        append("~")
                                        append(formattedEndAt.toString())
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(16.dp))

                                Text(
                                    "[리뷰어 발표]",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF7a7a7a)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    buildAnnotatedString {
                                        val announceAtToJava = Instant.fromEpochMilliseconds(
                                            generalField.recruitmentStartAt
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                            .toJavaLocalDateTime()

                                        val formattedAnnounceAt = announceAtToJava.format(
                                            DateTimeFormatter.ofPattern(
                                                "yy.MM.dd"
                                            )
                                        )

                                        append(formattedAnnounceAt.toString())
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(16.dp))

                                Text(
                                    "[리뷰 등록 기간]",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF7a7a7a)
                                )
                                Spacer(Modifier.height(8.dp))

                                Text(
                                    buildAnnotatedString {
                                        val reviewStartAtToJava = Instant.fromEpochMilliseconds(
                                            generalField.reviewStartAt
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                            .toJavaLocalDateTime()

                                        val formattedStartAt = reviewStartAtToJava.format(
                                            DateTimeFormatter.ofPattern(
                                                "yy.MM.dd"
                                            )
                                        )

                                        val reviewEndAtToJava = Instant.fromEpochMilliseconds(
                                            generalField.reviewEndAt
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                            .toJavaLocalDateTime()

                                        val formattedEndAt = reviewEndAtToJava.format(
                                            DateTimeFormatter.ofPattern(
                                                "yy.MM.dd"
                                            )
                                        )
                                        append(formattedStartAt.toString())
                                        append("~")
                                        append(formattedEndAt.toString())
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(16.dp))

                                Text(
                                    "[캠페인 마감]",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF7a7a7a)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    buildAnnotatedString {
                                        val endAtToJava = Instant.fromEpochMilliseconds(
                                            generalField.endAt
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                            .toJavaLocalDateTime()

                                        val formattedEndAt = endAtToJava.format(
                                            DateTimeFormatter.ofPattern(
                                                "yy.MM.dd"
                                            )
                                        )

                                        append(formattedEndAt.toString())
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )
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
                }

                // review offer button
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            end = 16.dp,
                            bottom = 120.dp,
                        ),
                    visible = userType == UserType.INFLUENCER,
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
                ) {
                    FloatingActionButton(
                        onClick = { showOfferDialog = true },
                        modifier = Modifier
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
                }


                // fetch reviewOffers button
                FloatingActionButton(
                    onClick = {
                        viewModel.fetchOffers()
                        showSheet = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 56.dp, end = 16.dp)
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
                if (showOfferDialog) {
                    Dialog(onDismissRequest = { showOfferDialog = false }) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            tonalElevation = 8.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Column(
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
                                        showOfferDialog = false
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = "신청 목록",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(
                                    16.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(influencerInfos) { info ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val unifiedCode = info.influencerMainProfileImageUrl
                                    if (unifiedCode != null) {
                                        AsyncImage(
                                            model = ImageRequest
                                                .Builder(LocalContext.current)
                                                .data(
                                                    "http://192.168.100.89:8080/open/influencer/image/profile/$unifiedCode"
                                                )
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "Advertisement Image",
                                            modifier = Modifier
                                                .size(64.dp)
                                                .clip(
                                                    CircleShape
                                                )
                                                .clickable {
                                                    // 👉 goto profile
                                                    navController.navigate(
                                                        ScreenRoute.MAIN_PROFILE_INFLUENCER.route
                                                        + "/${info.influencerId}"
                                                    )
                                                },
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            modifier = Modifier
                                                .size(64.dp)
                                                .clip(CircleShape)
                                                .background(Color.LightGray),
                                            imageVector = Icons.Default.People,
                                            contentDescription = "profile image "
                                        )
                                    }


                                    Spacer(Modifier.width(16.dp))

                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = "@${info.influencerLoginId}",
                                        )
                                        Text(
                                            buildAnnotatedString {
                                                append(info.offer)
                                                append("\n")
                                                withStyle(
                                                    style = MaterialTheme.typography.labelSmall
                                                        .toSpanStyle().copy(
                                                        color = Color.LightGray
                                                    )
                                                ) {
                                                    append(
                                                        Instant.fromEpochMilliseconds(
                                                            info.offerCreatedAt
                                                        ).toLocalDateTime(
                                                            TimeZone.currentSystemDefault()
                                                        ).date.toString()
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}