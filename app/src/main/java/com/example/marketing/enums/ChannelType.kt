package com.example.marketing.enums

import com.example.marketing.enums.DeliveryCategory.entries

enum class ChannelType(val code: Int) {
    BLOGGER(0),
    YOUTUBER(1),
    INSTAGRAM(2),
    THREAD(3);

    companion object {
        val codeMap: Map<Int, ChannelType> = ChannelType.entries.associateBy { it.code }

        fun fromCode(code: Int): ChannelType? = codeMap[code]
    }
}