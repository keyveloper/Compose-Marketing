package com.example.marketing.enums

import com.example.marketing.R

enum class ChannelIcon(
    val code: Int,
    val painterId: Int,
    val description: String,
    val title: String) {

    BLOGGER(0, R.drawable.naver_blog, "naver blog icon", "블로그"),
    YOUTUBER(1, R.drawable.youyube_fiiled, "youtube icon", "유튜브"),
    INSTAGRAM(2, R.drawable.insta_filled, "instagram icon", "인스타그램"),
    THREAD(3, R.drawable.thread_filled, "thread icon", "쓰레드");

    companion object {
        fun fromCode(code: Int): ChannelIcon? {
            return entries.firstOrNull { it.code == code }
        }
        // null 일때 Throw 처리
    }
}