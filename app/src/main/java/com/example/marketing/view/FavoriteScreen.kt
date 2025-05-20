package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.FavoriteViewStatus
import com.example.marketing.enums.UserType
import com.example.marketing.ui.color.SeaGreen
import com.example.marketing.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    userId: Long,
    userType: UserType
) {
    // ----------- 📌 fixed ---------
    val tabs = listOf(
        FavoriteViewStatus.MY_OFFER,
        FavoriteViewStatus.FAVORITE,
        FavoriteViewStatus.FOLLOW
    )

    // ------------🔃 status ------------
    var selectedTabIdx by remember { mutableStateOf(0) }
    val favoriteViewStatus by viewModel.favoriteViewStatus.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SeaGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(), //📌 no horizontal padding
        ) {
            TabRow(
                selectedTabIndex = selectedTabIdx,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    // Draw the indicator under the selected tab
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIdx]),
                        color = MaterialTheme.colorScheme.primary,
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, status ->
                    Tab(
                        selected = selectedTabIdx == index,
                        onClick = {
                            selectedTabIdx = index
                            viewModel.updateViewStatus(status)
                        },
                        text = { Text(status.title) }
                    )
                }
            }

            when(favoriteViewStatus) {
                FavoriteViewStatus.MY_OFFER -> {
                    InfluencerValidAdScreen(
                        navController = navController
                    )
                }

                FavoriteViewStatus.FAVORITE -> {
                    InfluencerFavoriteAdScreen(
                        navController = navController
                    )
                }

                FavoriteViewStatus.FOLLOW -> {

                }

                else -> {

                }
            }
        }

    }
}