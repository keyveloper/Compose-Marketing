package com.example.marketing.enums

enum class HomeScreenStatus(val code: Int) {
    Event(0),
    Delivery(1),
    Type(2),
    Platform(3);

    companion object {
        fun fromCode(code: Int): HomeScreenStatus? {
            return HomeScreenStatus.entries.firstOrNull { it.code == code }
        }
        // null 일때 Throw 처리
    }
}