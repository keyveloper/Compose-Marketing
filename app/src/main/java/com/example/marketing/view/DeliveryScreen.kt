package com.example.marketing.view


import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose .runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.DeliveryCategoryIcon
import com.example.marketing.ui.color.*
import com.example.marketing.ui.component.AdvertisementThumbnailItem
import com.example.marketing.ui.component.VerticalAdvertisementThumbnail
import com.example.marketing.ui.widget.DeliveryCategoryCard
import com.example.marketing.viewmodel.DeliveryViewModel

@Composable
fun DeliveryScreen(
    viewModel: DeliveryViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    val apiCallStatus = viewModel.apiCAllStatus.collectAsState()
    val liveAdvertisements = viewModel.liveAdvertisements.collectAsState()
    val selectedCategory = viewModel.currentCategoryStatus.collectAsState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index ==
                    scrollState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(selectedCategory.value) {
        viewModel.fetchTimelineInitByCategory()
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            viewModel.fetchTimelineNextByCategory()
        }
    }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(horizontal = 16.dp,),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize()
                    .height(150.dp)
                    .background(PastelLilac),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.headlineSmall
                )

                val categoryScrollState = rememberScrollState()
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .horizontalScroll(categoryScrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categoryIcons:List<DeliveryCategoryIcon> = listOf(
                        DeliveryCategoryIcon.LIFE,
                        DeliveryCategoryIcon.FOOD,
                        DeliveryCategoryIcon.DIGITAL,
                        DeliveryCategoryIcon.BEAUTY,
                        DeliveryCategoryIcon.FASSION,
                        DeliveryCategoryIcon.BOOK,
                        DeliveryCategoryIcon.SERVICE,
                        DeliveryCategoryIcon.PET
                    )
                    categoryIcons.forEach {
                        DeliveryCategoryCard(it) { viewModel.updateCurrentCategoryStatus(
                            DeliveryCategory.fromCode(it.code)!!
                        ) }
                    }
                }
            }
        }

        items(liveAdvertisements.value.chunked(2)) { rows ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val items = rows.map { advertisement ->
                    AdvertisementThumbnailItem.of(advertisement)
                }

                items.forEach { item ->
                    VerticalAdvertisementThumbnail(
                        item = item,
                        onClick = {}
                    )
                }

                if (items.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

        }
    }
}