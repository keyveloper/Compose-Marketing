package com.example.marketing.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashV1Screen(
    onSplashDone: () -> Unit
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(5000)
        onSplashDone()
    }

    // draw Splash Screen !
}