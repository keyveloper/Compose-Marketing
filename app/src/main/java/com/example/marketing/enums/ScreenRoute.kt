package com.example.marketing.enums

enum class ScreenRoute(val route: String) {
    // start
    SPLASH("splash"),
    SPLASH_v1("splash_v1"),

    // Auth
    AUTH("auth"),
    AUTH_HEALTH("auth-health"),
    AUTH_HOME("auth-home"),
    AUTH_INFLUENCER_LOGIN("auth-login"),
    AUTH_INFLUENCER_SIGNUP("auth-signup"),
    AUTH_ADVERTISER_LOGIN("auth-advertiser-login"),
    AUTH_ADVERTISER_SIGNUP("auth-advertiser-signup"),

    // MAIN SCOPE = GLOBAL SCOPE
    MAIN("main"),
    MAIN_INIT("main-init"),
    MAIN_INIT_INFLUENCER("main-init-influencer"),
    MAIN_INIT_ADVERTISER("main-init-advertiser"),
    MAIN_HOME("main-home"),
    MAIN_GOLDEN("golden")

}