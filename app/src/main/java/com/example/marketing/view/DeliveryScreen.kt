package com.example.marketing.view


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
                   // categoryIcons.forEach {
                   //     DeliveryCategoryCard(it) { viewModel.updateCurrentCategoryStatus(
                  //          DeliveryCategory.fromCode(it.code)!!
                 //       ) }
                  //  }
                }
            }
        }
    }
}