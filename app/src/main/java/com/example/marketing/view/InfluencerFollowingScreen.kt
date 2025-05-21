package com.example.marketing.view
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.state.FollowingFeedFetchState
import com.example.marketing.ui.color.SeaGreen
import com.example.marketing.viewmodel.InfluencerFollowingViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfluencerFollowingScreen(
    navController: NavController,
    viewModel: InfluencerFollowingViewModel = hiltViewModel()
) {
    // ------------🔃 status ------------
    val state by viewModel.initFeedFetchSTate.collectAsStateWithLifecycle()

    // ------------🔨 util -------------
    var showOfferSheet by remember { mutableStateOf(false) }
    val offerSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // ----------- 🚀 from server value -----------
    val offerInfos by viewModel.offerInfos.collectAsState()

    when (state) {
        is FollowingFeedFetchState.Loading -> CommonLoadingView()
        is FollowingFeedFetchState.Error -> CommonErrorView(
            message = (state as FollowingFeedFetchState.Error).msg,
            onRetry = { viewModel.refresh }
        )
        is FollowingFeedFetchState.Ready -> {
            val feeds = (state as FollowingFeedFetchState.Ready).feedItems

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // Feed
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(feeds) { feed ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // User row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val url =
                                    "http://192.168.100.89:8080/open/advertiser/image/profile/${feed.advertiserProfileUnifiedCode}"
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "user Profile image",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                        .clickable {
                                            navController.navigate(
                                                ScreenRoute.MAIN_PROFILE_ADVERTISER.route +
                                                "/${feed.advertiserId}/INFLUENCER"
                                            )
                                        }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "@${feed.advertiserLoginId}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            // Image
                            val imageUris = feed.adImageUris
                            val pagerState = rememberPagerState(pageCount = { imageUris.size })

                            VerticalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxWidth()
                                    .height(400.dp)
                            ) { page ->
                                val unifiedCode = imageUris[page]?.substringAfterLast("/")
                                Log.i("followingScreen", "unifiedCode: ${unifiedCode}")
                                val url = "http://192.168.100.89:8080/open/advertisement/image/$unifiedCode"
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Feed image $page",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            navController.navigate(
                                                ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                                "/${feed.advertisementId}/INFLUENCER"
                                            )
                                        }
                                )
                            }

                            // Action buttons
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                IconButton(
                                    onClick = {
                                        viewModel.favoriteAd(feed.advertiserId)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.FavoriteBorder,
                                        contentDescription = "Like"
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        showOfferSheet = true
                                        viewModel.fetchOfferInfos(feed.advertisementId)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.ChatBubbleOutline,
                                        contentDescription = "Offer"
                                    )

                                }
                            }

                            // Caption
                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = "[제품 이름] ${feed.itemName}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = "[제공 내역] ${feed.itemIfo}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = buildAnnotatedString {
                                    val createdAt = Instant.fromEpochMilliseconds(
                                        feed.advertisementCreatedAt
                                    ).toLocalDateTime(TimeZone.currentSystemDefault())
                                        .toJavaLocalDateTime()

                                    val formattedCreatedAt = createdAt.format(
                                        DateTimeFormatter.ofPattern(
                                            "yy년 MM월 dd일"
                                        )
                                    )

                                    withStyle(
                                        style = MaterialTheme.typography.labelSmall
                                            .toSpanStyle().copy(
                                            color = Color.LightGray
                                        )
                                    ) {
                                        append(formattedCreatedAt)
                                    }
                                }
                            )
                        }
                    }
                }

                // offer Infos
                if (showOfferSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showOfferSheet = false },
                        sheetState = offerSheetState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopCenter),
                                text = "리뷰 목록",
                                style = MaterialTheme.typography.headlineSmall,
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .padding(
                                        top = 48.dp,
                                        bottom = 24.dp,
                                        start = 16.dp
                                    ),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(offerInfos) { info ->
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
}

