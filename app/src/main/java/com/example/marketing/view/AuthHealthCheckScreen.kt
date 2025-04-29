package com.example.marketing.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType
import com.example.marketing.viewmodel.AuthHealthCheckViewModel

@Composable
fun AuthHealthCheckScreen(
    viewModel: AuthHealthCheckViewModel = hiltViewModel(),
    navController: NavController
) {
    val token = viewModel.tokenFlow.collectAsState()
    val loginStatus = viewModel.loginStatus.collectAsState()
    val userType = viewModel.userType.collectAsState()
    val userId = viewModel.userId.collectAsState()

    LaunchedEffect(token.value) {
        token.value?.let {
            viewModel.validateToken()
        }
    }


    LaunchedEffect(loginStatus.value) {
        if (loginStatus.value) { // login
            when (userType.value) {
                UserType.INFLUENCER -> {
                    navController.navigate(
                        ScreenRoute.MAIN_INIT.route +
                                "/${userType.value}" + "/${userId.value}"
                        // enum -> String ??
                    )
                }
                UserType.ADVERTISER_COMMON -> {

                }

                else -> {
                    Log.w(
                        "AuthHealthCheckScreen" ,
                        "this userType is not allowed ${userType.value}")
                }
            }
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