package com.example.marketing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marketing.view.AdminLoginScreen
import com.example.marketing.view.AdminSignUpScreen
import com.example.marketing.view.AuthHomeScreen

@Composable
fun AuthNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "auth-graph"

    ) {

        navigation(startDestination = "auth-admin-sign-up", route = "auth-graph") {
            composable("auth-home") {
                AuthHomeScreen(navController)
            }

            composable("auth-influencer") {

            }

            composable("auth-advertiser") {

            }

            composable("auth-admin-sign-up") {
                AdminSignUpScreen(navController = navController)
            }

            composable("auth-admin-login") {
                AdminLoginScreen()
            }
        }

        navigation(startDestination = "main-home", route = "main-graph") {
            composable(
                route = "main-admin/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.LongType }
                )) {
            }

            composable("main-influencer") {

            }

            composable("main-advertiser") {

            }
        }
    }
}