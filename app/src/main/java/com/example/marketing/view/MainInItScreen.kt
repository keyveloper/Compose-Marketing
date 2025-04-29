package com.example.marketing.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.marketing.enums.UserType

@Composable
fun MainInItScreen(
    userId: Long,
    userType: UserType,
    navController: NavController
) {
    LaunchedEffect(userType) {
        when (userType) {
            UserType.INFLUENCER -> {

            }

            UserType.ADVERTISER_COMMON -> {

            }

            else -> {
                Log.w("MainInitScreen", "Warn This UserType is not allowed: $userType")
            }
        }
    }
}