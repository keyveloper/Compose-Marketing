package com.example.marketing.enums

enum class ScreenRoute(val route: String) {
    // start
    SPLASH("splash"),
    SPLASH_v1("splash_v1"),

    // Auth
    AUTH("auth"),
    AUTH_HEALTH("auth_health"),
    AUTH_HOME("auth_home"),
    AUTH_INFLUENCER_LOGIN("auth_login"),
    AUTH_INFLUENCER_SIGNUP("auth_signup"),
    AUTH_ADVERTISER_LOGIN("auth_advertiser_login"),
    AUTH_ADVERTISER_SIGNUP("auth_advertiser_signup"),

    // MAIN SCOPE = GLOBAL SCOPE
    MAIN("main"),

}