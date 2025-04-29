package com.example.marketing.ui.component.bar

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.*
import androidx.navigation.NavController
import com.example.marketing.enums.MainScreenStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    onStatusChange: (MainScreenStatus) -> Unit,
    userType: UserType
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onStatusChange(MainScreenStatus.LOCATION_MAP) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location Icon",
                    tint = Color.Gray
                )
            }
            IconButton(
                onClick = { onStatusChange(MainScreenStatus.GOLDEN) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = "GoldenKeyword Screen Icon",
                    tint = Color.Gray
                )
            }


            IconButton(
                onClick = { onStatusChange(MainScreenStatus.HOME) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home Icon",
                    tint = Color.Gray,
                )
            }


            // change when advertiser
            when (userType) {
                UserType.INFLUENCER -> {
                    IconButton(
                        onClick = { onStatusChange(MainScreenStatus.FOLLOW) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "my advertisement",
                            tint = Color.Gray
                        )
                    }
                }

                UserType.ADVERTISER_COMMON -> {
                    IconButton(
                        onClick = { onStatusChange(MainScreenStatus.ADVERTISER_WRITE) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "advertiser write Icon for advertiser",
                            tint = Color.Gray
                        )
                    }
                }

                else -> {}
            }


            IconButton(
                onClick = { onStatusChange(MainScreenStatus.PROFILE) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile Icon",
                    tint = Color.Gray
                )
            }
        }
    }
}