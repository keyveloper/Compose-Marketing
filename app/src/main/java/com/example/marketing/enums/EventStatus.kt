package com.example.marketing.enums

import com.example.marketing.enums.ChannelIcon.entries

enum class EventStatus(val code: Int) {
    FRESH(0),
    HOT(1),
    DEADLINE(2);

    companion object {
        fun fromCode(code: Int): EventStatus? {
            return EventStatus.entries.firstOrNull { it.code == code }
        }
        // null 일때 Throw 처리
    }
}