package com.example.marketing.enums

enum class GoldenKeywordFetchStatus(val code: Int, val message: String) {
    IDLE(0, "Init"),
    LOADING(1, "Fetching Data ..."),
    SUCCESS(2, "Success"),
    ERROR(3, "Fetch Error see the error logs...")
}