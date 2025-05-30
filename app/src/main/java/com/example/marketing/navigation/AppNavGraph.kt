package com.example.marketing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType
import com.example.marketing.view.AdvertisementDetailScreen
import com.example.marketing.view.AdvertiserLoginScreen
import com.example.marketing.view.AdvertiserProfileControlScreen
import com.example.marketing.view.AdvertiserSignUpScreen
import com.example.marketing.view.AuthHealthCheckScreen
import com.example.marketing.view.AuthHomeScreen
import com.example.marketing.view.InfluencerLoginScreen
import com.example.marketing.view.InfluencerProfileControlScreen
import com.example.marketing.view.InfluencerSignUpScreen
import com.example.marketing.view.MainScreen
import com.example.marketing.view.SplashV1Screen

@Composable
fun AppNavGraph(
    navController: NavHostController= rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SPLASH.route
    ) {
        navigation(
            startDestination = ScreenRoute.SPLASH_v1.route,
            route = ScreenRoute.SPLASH.route) {

            composable(ScreenRoute.SPLASH_v1.route) {
                SplashV1Screen(navController)
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

            composable(ScreenRoute.AUTH_HOME.route) {
                AuthHomeScreen(
                    toInfluencer = {
                        navController.navigate(ScreenRoute.AUTH_INFLUENCER_LOGIN.route)
                    },
                    toAdvertiser = {
                        navController.navigate(ScreenRoute.AUTH_ADVERTISER_LOGIN.route)
                    }
                )
            }

            composable(ScreenRoute.AUTH_ADVERTISER_LOGIN.route) {
                AdvertiserLoginScreen(
                    navController = navController
                )
            }

            composable(ScreenRoute.AUTH_INFLUENCER_LOGIN.route) {
                InfluencerLoginScreen(
                    navController = navController
                )
            }

            composable(ScreenRoute.AUTH_ADVERTISER_SIGNUP.route) {
                AdvertiserSignUpScreen(
                    navController = navController
                )
            }

            composable(ScreenRoute.AUTH_INFLUENCER_SIGNUP.route) {
                InfluencerSignUpScreen(
                    navController = navController
                )
            }
        }

        navigation(
            startDestination = ScreenRoute.MAIN_INIT.route,
            route = ScreenRoute.MAIN.route
        ) {
            composable(ScreenRoute.MAIN_INIT.route) {
                // do noting
            }

            composable(
                route = ScreenRoute.MAIN_INIT.route + "/{userType}/{userId}",
                arguments = listOf(
                    navArgument("userType") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.LongType }
                    )
            ) { backStackEntry ->
                val userTypeArg = backStackEntry.arguments?.getString("userType")
                val userType = try {
                    enumValueOf<UserType>(userTypeArg ?: "")
                } catch (e: IllegalArgumentException) {
                    UserType.ADVERTISER_COMMON // fallback
                }
                val userId = backStackEntry.arguments?.getLong("userId")
                    ?: -1L
                MainScreen(
                    userInitType = userType,
                    initUserId = userId,
                    navController = navController
                )
            }

            composable(
                route = ScreenRoute.MAIN_HOME_AD_DETAIL.route + "/{advertisementId}/{userType}",
                arguments = listOf(
                    navArgument("advertisementId") { type = NavType.LongType },
                    navArgument("userType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                AdvertisementDetailScreen(
                    navController = navController
                )
            }

            composable(
                route = ScreenRoute.MAIN_PROFILE_INFLUENCER.route + "/{influencerId}",
                arguments = listOf(navArgument("influencerId") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                val influencerId = backStackEntry.arguments?.getLong("influencerId") ?: 0
                InfluencerProfileControlScreen(
                    navController = navController,
                    influencerId = influencerId
                )
            }


            composable(
                route = ScreenRoute.MAIN_PROFILE_ADVERTISER.route + "/{advertiserId}/{userType}",
                arguments = listOf(
                    navArgument("advertiserId") { type = NavType.LongType },
                    navArgument("userType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                AdvertiserProfileControlScreen(
                    navController = navController,
                )
            }
        }
    }
}