package com.example.marketing.enum

import com.example.marketing.R

enum class ChannelType(val code: Long, val iconRes: Int) {
    BLOGGER(0, R.drawable.naver_blog),
    YOUTUBER(1, R.drawable.youyube_fiiled),
    INSTAGRAM(2, R.drawable.insta_filled),
    THREAD(3, R.drawable.thread_filled)
}