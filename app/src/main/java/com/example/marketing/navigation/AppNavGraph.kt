package com.example.marketing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.view.AuthHealthCheckScreen
import com.example.marketing.view.MainScreen
import com.example.marketing.view.SplashV1Screen

@Composable
fun AppNavGraph(
    navController: NavHostController= rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SPLASH.route
    ) {
        navigation(
            startDestination = ScreenRoute.SPLASH_v1.route,
            route = ScreenRoute.SPLASH.route) {

            composable(ScreenRoute.SPLASH_v1.route) {
                SplashV1Screen(
                    onSplashDone = {
                        navController.navigate(ScreenRoute.AUTH.route)
                    }
                )
            }
        }

        navigation(
            startDestination = ScreenRoute.AUTH_HEALTH.route,
            route = ScreenRoute.AUTH.route
        ) {
            composable(ScreenRoute.AUTH_HEALTH.route) {
                AuthHealthCheckScreen(
                    navController = navController
                )
            }

            composable(ScreenRoute.AUTH_LOGIN.route) {

            }

            composable(ScreenRoute.AUTH_SIGNUP.route) {

            }
        }

        navigation(
            startDestination = ScreenRoute.MAIN.route,
            route = ScreenRoute.MAIN.route
        ) {
            composable(ScreenRoute.MAIN.route) {
                MainScreen()
            }
        }
    }
}