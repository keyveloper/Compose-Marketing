package com.example.marketing.ui.component.bar

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.*
import com.example.marketing.enums.MainScreenStatus

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    onSelected: (MainScreenStatus) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Location Icon",
                tint = Color.Gray
            )

            Icon(
                imageVector = Icons.Outlined.Map,
                contentDescription = "Map Icon",
                tint = Color.Gray
            )

            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Home Icon",
                tint = Color.Gray,
            )

            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "my advertisement",
                tint = Color.Gray
            )

            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Profile Icon",
                tint = Color.Gray
            )
        }
    }
}