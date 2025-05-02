package com.example.marketing.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.marketing.enums.AdWritingStatus
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.enums.ReviewType
import com.example.marketing.ui.color.CottonCandy
import com.example.marketing.ui.color.MintCream
import com.example.marketing.ui.color.PastelBerry
import com.example.marketing.ui.color.PastelTeal
import com.example.marketing.ui.color.PersianPastel
import com.example.marketing.ui.color.SeaGreen
import com.example.marketing.ui.color.SoftGray
import com.example.marketing.ui.color.SunOrange
import com.example.marketing.ui.color.WatermelonSorbet
import com.example.marketing.ui.component.DeliveryCategorySelectionCard
import com.example.marketing.ui.component.RangeDatePicker
import com.example.marketing.ui.component.SingleDatePicker
import com.example.marketing.viewmodel.AdvertisementWritingViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun AdvertisementWritingScreen(
    modifier: Modifier = Modifier,
    viewModel: AdvertisementWritingViewModel = hiltViewModel(),
) {
    // ---------  👉 status  ----------------
    val writingStatus = viewModel.writingStatus.collectAsState()
    val saveApiStatus = viewModel.saveApiStatus.collectAsState()

    // ----------  ✍️ info value ------------
    val title = viewModel.title.collectAsState()
    val selectedChannelType = viewModel.channelType.collectAsState()
    val selectedReviewType = viewModel.reviewType.collectAsState()
    val itemName = viewModel.itemName.collectAsState()
    val itemInfo = viewModel.itemInfo.collectAsState()
    val recruitmentNumber = viewModel.recruitmentNumber.collectAsState()
    val siteUrl = viewModel.siteUrl.collectAsState()
    val keywords = viewModel.keywords.collectAsState()

    // ----------- categories ----------
    var showCategorySelector by remember { mutableStateOf(false) }
    val selectedCategory by remember {
        mutableStateOf(DeliveryCategory.ALL)
    }
    val categories = viewModel.categories.collectAsState()

    // ----------  📷 image & pager ---------------
    // image Picker
    var selectedPage by remember { mutableStateOf(0) }
    val imageItems = viewModel.imageItems.collectAsState()
    val thumbnail = viewModel.thumbnail.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(5),
        onResult = { uris: List<Uri>->
            if (uris.isNotEmpty()) {
                if (imageItems.value.size >= 5) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Can't add more images.")
                    }
                    return@rememberLauncherForActivityResult
                } else {
                    for (uri in uris) {
                        coroutineScope.launch {
                            viewModel.uploadImage(uri)
                        }
                    }
                }
            }
        }
    )

    // ---------- 📌 Launched Effect ------------
    LaunchedEffect(writingStatus) {
        when (writingStatus.value) {
            AdWritingStatus.INIT -> viewModel.issueDraft()
            AdWritingStatus.SAVED -> {
                viewModel.updateWritingStatus(AdWritingStatus.INIT)
                viewModel.resetPage()
            }

            else -> {}
        }
    }

    LaunchedEffect(saveApiStatus) {
        viewModel.updateWritingStatus(AdWritingStatus.SAVED)
    }

    // ------------🖼️ UI ----------------

    AnimatedVisibility( // 🚀 issued DRAFT!
        visible = writingStatus.value == AdWritingStatus.DRAFT_ISSUED,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        // total page scroll
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .background(PersianPastel),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // # image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SeaGreen)
            ) {
                val pagerState = rememberPagerState(
                    pageCount = { imageItems.value.size.coerceAtLeast(1) }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .background(PastelBerry)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(500.dp)
                            .background(MintCream)
                    ) { currentPage ->
                        Log.i("advertisementWriting", "currentPage: $currentPage")
                        selectedPage = currentPage
                        val currentItem = imageItems.value.getOrNull(currentPage)

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(CottonCandy)
                        ) {

                            AsyncImage(
                                model = currentItem?.localUri,
                                contentDescription = "Advertisement Image added",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 16.dp, bottomEnd = 16.dp
                                        )
                                    )
                                    .background(Color.White)
                                    .border(
                                        width = 3.dp,
                                        color = SoftGray,
                                        shape = RoundedCornerShape(
                                            bottomStart = 16.dp,
                                            bottomEnd = 16.dp
                                        )
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    if (imageItems.value.isNotEmpty()) {
                        val targetItem = imageItems.value.getOrNull(selectedPage)
                        OutlinedIconButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.withdrawUploadingImage(targetItem)
                                } },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "image delete icon "
                            )
                        }

                        OutlinedButton(
                            onClick = { viewModel.updateThumbnail(targetItem) },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .width(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            colors = if (targetItem == thumbnail.value) {
                                // Simulate filled appearance
                                ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color(0xFFB2EBF2), // pastel blue or your themed fill
                                    contentColor = Color.Black
                                )
                            } else {
                                // Default outlined look
                                ButtonDefaults.outlinedButtonColors()
                            }
                        ) {
                            if (targetItem == thumbnail.value) {
                                Text("✅")
                            } else Text("👉 썸네일")
                        }
                    }
                }

                FilledIconButton(
                    enabled = imageItems.value.size < 5,
                    shape = CircleShape,                // makes it perfectly round
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MintCream,     // background
                        contentColor   = Color.White    // “＋” icon tint
                    ),
                    modifier = Modifier
                        .size(56.dp)                    // touch-friendly
                        .shadow(                        // subtle depth
                            elevation = 6.dp,
                            shape = CircleShape,
                            clip = false                // keep the blur outside the clip
                        )
                        .align(Alignment.BottomCenter),
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add new Image"
                    )
                }
            }


            // # Title & Details
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .background(SunOrange)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp)
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { channelExpended = true }
                                ,
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

                    if (selectedReviewType.value == ReviewType.DELIVERY) {

                        val interaction = remember { MutableInteractionSource() }

                        Box(                                   // 1️⃣ clickable wrapper
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showCategorySelector = true }  // ← works
                        ) {
                            OutlinedTextField(                 // 2️⃣ decorate only
                                value = if (categories.value.isEmpty())
                                    "" else "선택됨 ${categories.value.size}개",
                                onValueChange = {},            // no typing
                                readOnly = true,
                                enabled  = false,              // ⬅️ IMPORTANT: stop consuming touch
                                label = { Text("👉 카테고리 선택하기") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }


                        Log.i("adWritingView",
                            "showCategorySelector: ${showCategorySelector}")
                        if (showCategorySelector) {
                            DeliveryCategorySelectionCard(
                                onAdd = { viewModel.addCategory(selectedCategory) },
                                onDelete = { viewModel.deleteCategory(selectedCategory) }
                            )
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title.value,
                        onValueChange = {
                            viewModel.updateTitle(it)
                        },
                        label = { Text("광고글 제목") },
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = itemName.value,
                        onValueChange = { viewModel.updateItemName(it) },
                        label = { Text("제품 이름") },
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        value = itemInfo.value ?: "",
                        onValueChange = { viewModel.updateItemInfo(it) },
                        label = { Text("제품 소개(Optional)") },
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = siteUrl.value ?: "",
                        onValueChange = { viewModel.updateSiteUrl(it) },
                        label = { Text("상세 페이지 혹은 플레이스 (Optional)") }
                    )
                }
            }

            // # apply  info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp)
                    .padding(16.dp)
                    .background(SeaGreen)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = recruitmentNumber.value?.toString() ?: "",
                        onValueChange = {
                            val number = it.toIntOrNull()
                            if (number != null) {
                                viewModel.updateRecruitmentNumber(number)
                            }
                        },
                        label = { Text("모집 인원") },
                    )

                    RangeDatePicker(
                        label = "모집기간",
                        modifier = Modifier.fillMaxWidth(),
                        onDateRangeSelected = { start, end ->
                            viewModel.updateRecruitStartAt(start)
                            viewModel.updateRecruitEndAt(end)
                        }
                    )

                    SingleDatePicker(
                        label = "리뷰어 발표일",
                        modifier = Modifier.fillMaxWidth(),
                        onDateSelected = { selectedMillis ->
                            viewModel.updateAnnouncementAt(selectedMillis)
                        }
                    )

                    RangeDatePicker(
                        label = "컨텐츠 등록기간",
                        modifier = Modifier.fillMaxWidth(),
                        onDateRangeSelected = { start, end ->
                            viewModel.updateReviewStartAt(start)
                            viewModel.updateReviewEndAt(end)
                        }
                    )

                    SingleDatePicker(
                        label = "캠파인 종료",
                        modifier = Modifier.fillMaxWidth(),
                        onDateSelected =  { selectedMillis ->
                            viewModel.updateReviewEndAt(selectedMillis)
                        }
                    )
                }
            }

            // keyword
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp)
                    .background(WatermelonSorbet)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "희망 키워드 등록",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        var newKeyword by remember { mutableStateOf("") }
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.7f),
                            enabled = keywords.value.size < 5,
                            value = newKeyword,
                            onValueChange = { newKeyword = it },
                            label = { Text("최대 5개 입력가능") },
                        )

                        OutlinedButton(
                            enabled = keywords.value.size < 5,
                            modifier = Modifier
                                .weight(0.3f)
                                .size(56.dp)
                            ,
                            onClick = {
                                viewModel.addKeyword(newKeyword)
                                newKeyword = ""
                            },
                            shape = RectangleShape
                        ) { Text("추가") }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        keywords.value.forEach { keyword ->
                            Text(
                                text = "#$keyword",
                                modifier = Modifier
                                    .background(
                                        color = SeaGreen,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(8.dp)
                                    .clickable { viewModel.deleteKeyword(keyword) }
                                ,
                                color = PastelTeal,
                            )
                        }
                    }
                }
            }

            // button
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 0.dp)
                    .offset(y = (-16).dp)
                    .height(50.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { coroutineScope.launch {
                        viewModel.uploadAdvertisement()
                    } },
                    enabled = viewModel.checkEssentialFields()
                ) { Text("광고 등록") }
            }
        }
    }

    AnimatedVisibility( // 😏 INIT
        visible = writingStatus.value == AdWritingStatus.INIT,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { coroutineScope.launch { viewModel.issueDraft() }},
                    modifier = Modifier
                        .size(48.dp) // 👈 standard touch target size
                        .background(
                            color = Color(0xFFE0F7FA),        // soft aqua background
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Issue Draft again",
                        tint = Color(0xFF00796B)             // teal-like tint
                    )
                }
                Text("😏 무언가 잘못된 것 같아요")
            }
        }
    }
}
