package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.HomeScreenStatus
import com.example.marketing.ui.component.HomeTopBar
import com.example.marketing.viewmodel.HomeViewModel
import java.sql.Time

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val screenState = homeViewModel.screenState.collectAsState()
    // status Screen Surface
    Box(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        // top bar
        HomeTopBar(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onHomeBarSelected = { selectedStatus ->
                homeViewModel.changeState(selectedStatus)
            }
        )

        // content Screen Box
        Box(modifier = Modifier
            .padding(top = 68.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onTertiary)
        ) {
            when (screenState.value) {
                HomeScreenStatus.Event -> {
                    EventScreen()
                }
                
                HomeScreenStatus.Delivery -> {
                    
                }

                HomeScreenStatus.Type -> {

                }

                HomeScreenStatus.Platform -> {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
   }