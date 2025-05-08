package com.example.marketing.enums

enum class MainScreenStatus(val code: Int) {
    HOME(0),
    LOCATION_NEAR(1),
    LOCATION_MAP(2),
    FAVORITE(3),
    GOLDEN(4),
    PROFILE(5),
    ADVERTISER_WRITE(6);

    companion object {
        fun fromCode(code: Int): MainScreenStatus? {
            return MainScreenStatus.entries.firstOrNull { it.code == code }
        }
        // null 일때 Throw 처리
    }
}