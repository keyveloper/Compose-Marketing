package com.example.marketing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.marketing.view.AdminSignUpScreen
import com.example.marketing.view.AuthHomeViewModel

@Composable
fun AuthNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "auth-graph"

    ) {

        navigation(startDestination = "auth-admin", route = "auth-graph") {
            composable("auth-home") {
                AuthHomeViewModel(navController)
            }

            composable("auth-influencer") {

            }

            composable("auth-advertiser") {

            }

            composable("auth-admin") {
                AdminSignUpScreen(navController = navController)
            }
        }

        navigation(startDestination = "main-home", route = "main-graph") {
            
        }
    }
}