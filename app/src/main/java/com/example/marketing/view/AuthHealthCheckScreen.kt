package com.example.marketing.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.viewmodel.AuthHealthCheckViewModel

@Composable
fun AuthHealthCheckScreen(
    viewModel: AuthHealthCheckViewModel = hiltViewModel(),
    navController: NavController
) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn.value) {
        if (isLoggedIn.value) {
            navController.navigate(ScreenRoute.MAIN.route) {
                popUpTo(ScreenRoute.AUTH_HEALTH.route) { inclusive = true }
            }
        } else {
            navController.navigate(ScreenRoute.AUTH_HOME.route) {
                popUpTo(ScreenRoute.AUTH_HEALTH.route) { inclusive = true }
            }
        }
    }
}