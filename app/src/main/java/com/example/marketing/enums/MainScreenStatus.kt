package com.example.marketing.enums

enum class MainScreenStatus(val code: Int) {
    HOME(0),
    LOCATION_NEAR(1),
    LOCATION_MAP(2),
    FOLLOW(3),
    PROFILE(4);

    companion object {
        fun fromCode(code: Int): MainScreenStatus? {
            return MainScreenStatus.entries.firstOrNull { it.code == code }
        }
        // null 일때 Throw 처리
    }
}