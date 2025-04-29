package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.ui.color.PastelRose

@Composable
fun SplashV1Screen(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        navController.navigate(ScreenRoute.AUTH.route) {
            popUpTo(ScreenRoute.SPLASH_v1.route) {
                inclusive = true
            }
        }
    }

    // draw Splash Screen !
    Box(
        modifier = Modifier.fillMaxSize()
            .background(PastelRose),
        contentAlignment = Alignment.Center
    ) {
        Text("this is a splash screen!!")
    }
}