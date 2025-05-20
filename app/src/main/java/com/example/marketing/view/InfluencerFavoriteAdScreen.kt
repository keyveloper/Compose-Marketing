package com.example.marketing.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.viewmodel.InfluencerFavoriteAdViewModel

@Composable
fun InfluencerFavoriteAdScreen(
    viewModel: InfluencerFavoriteAdViewModel = hiltViewModel(),
    navController: NavController
) {
    // ----------- 🚀 api value -----------
    val favoriteAdItems by viewModel.favoriteAdItems.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(Unit) {
        viewModel.fetchFavoriteAds()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(favoriteAdItems.size) { index ->
                val item = favoriteAdItems[index]
                val uuid = item.thumbnailUrl.substringAfterLast('/')
                val url = "http://192.168.100.89:8080/open/advertisement/image/${uuid}"
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Advertisement Image",
                    modifier = Modifier.aspectRatio(1f)
                        .padding(0.5.dp)
                        .clickable {
                            navController.navigate(
                                ScreenRoute.MAIN_HOME_AD_DETAIL.route +
                                        "/${item.advertisementId}"
                            )
                                   },
                    contentScale   = ContentScale.Crop
                )
            }
        }
    }
}