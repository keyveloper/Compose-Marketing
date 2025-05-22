package com.example.marketing.view


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.DeliveryCategoryIcon
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType
import com.example.marketing.state.AdCategoryInitState
import com.example.marketing.state.FollowingFeedFetchState
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.VerticalAdvertisementThumbnail
import com.example.marketing.viewmodel.DeliveryViewModel

@Composable
fun DeliveryScreen(
    viewModel: DeliveryViewModel = hiltViewModel(),
    navController: NavController,
    userId: Long,
    userType: UserType
) {
    // ------------⛏️ init ---------------
    val initState by viewModel.initState.collectAsStateWithLifecycle()

    // ------------🔃 status ------------
    val scrollState = rememberLazyListState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    when (initState) {
        is AdCategoryInitState.Loading -> CommonLoadingView()
        is AdCategoryInitState.Error -> CommonErrorView(
            message = (initState as AdCategoryInitState.Error).msg,
            onRetry = { viewModel.refresh }
        )
        is AdCategoryInitState.Ready -> {
            val ads = (initState as AdCategoryInitState.Ready).pkgs

            val reachedBottom: Boolean by remember {
                derivedStateOf {
                    val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
                    lastVisibleItem?.index != 0 && lastVisibleItem?.index ==
                            scrollState.layoutInfo.totalItemsCount - 1
                }
            }

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp,),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // category Row
                item {
                    val categoryIcons:List<DeliveryCategoryIcon> = listOf(
                        DeliveryCategoryIcon.ALL,
                        DeliveryCategoryIcon.LIFE,
                        DeliveryCategoryIcon.FOOD,
                        DeliveryCategoryIcon.DIGITAL,
                        DeliveryCategoryIcon.BEAUTY,
                        DeliveryCategoryIcon.FASSION,
                        DeliveryCategoryIcon.BOOK,
                        DeliveryCategoryIcon.SERVICE,
                        DeliveryCategoryIcon.PET
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(categoryIcons.size) { index ->
                            val categoryIcon = categoryIcons[index]
                            val isSelected = categoryIcon.code == selectedCategory.code
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.updateSelectedCategory(
                                            DeliveryCategory.fromCode(categoryIcon.code)!!
                                        )

                                    }
                                    .padding(vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            color =
                                            if (isSelected)
                                                MaterialTheme.colorScheme.primary
                                            else Color.LightGray.copy(alpha = 0.2f),
                                                shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = categoryIcon.iconVector,
                                        contentDescription = categoryIcon.contentDescription,
                                        tint =
                                        if (isSelected) Color.White
                                        else Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = categoryIcon.name,
                                    style = MaterialTheme.typography.labelSmall,
                                    color =
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else Color.Gray
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(32.dp))
                }

                // ads
                items(ads.chunked(2)) { rowAds ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowAds.forEach { ad ->
                            val thumb = AdvertisementThumbnailItem.of(
                                ad.advertisementGeneralFields,
                                ad.advertisementGeneralFields
                                    .thumbnailUri?.substringAfterLast("/")
                            )
                            if (userType == UserType.INFLUENCER) {
                                VerticalAdvertisementThumbnail(
                                    item = thumb,
                                    onClick = { selectedThumb ->
                                        navController.navigate(
                                            ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                                    "/${selectedThumb.advertisementId}/${userType.name}"
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
                                                    "/${selectedThumb.advertisementId}/${userType.name}"
                                        )
                                    },
                                    onToggleFavorite = {

                                    }
                                )
                            }

                        }
                        // Fill empty space if only 1 item in last row
                        if (rowAds.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }


}