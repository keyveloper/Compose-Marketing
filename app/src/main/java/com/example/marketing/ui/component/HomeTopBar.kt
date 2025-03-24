package com.example.marketing.ui.component

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp

@Composable
fun HomeTopBar(
) {
    val items = listOf("Home", "Explore", "Trending", "Favorites", "Settings", "Profile")

    Surface(
        modifier = Modifier.padding(),
        color = Color.Green
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items.size) { index ->
                ScrollableTopBarItem(title = items[index])
            }
        }
    }
}

@Composable
fun ScrollableTopBarItem(title: String) {
    Text(
        text = title,
        color = Color.White,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium)
    )
}


@Preview
@Composable
fun PreviewHomeTopBar() {

}