package com.example.marketing.ui.component

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose .runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun BirthdaySelector() {
    val years = (1900..2025).map { it.toString() }
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val days = (1..31).map { it.toString().padStart(2, '0') }

    DefaultWheelPicker(
        items = years,
        selectedIndex = years.lastIndex,
    ) { }

}

@Composable
fun DefaultWheelPicker(
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChanged: (Int) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)

    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                onSelectedIndexChanged(index)
            }
    }

    Box(
        modifier = Modifier
            .height(200.dp)
            .width(100.dp)
            .padding(vertical = 15.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
                .background(Color.Gray),
            contentPadding = PaddingValues(vertical = 60.dp),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(items) { index, item ->
                val isSelected = listState.firstVisibleItemIndex == index

                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = if (isSelected) Color.Blue else Color.Gray,
                    fontSize = if (isSelected) 20.sp else 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(36.dp)
                .background(Color.Transparent)
                .border(1.dp, Color.LightGray)
        )
    }
}