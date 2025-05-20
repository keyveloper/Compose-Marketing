package com.example.marketing.enums

enum class UserType(val code: Int) {
    ADMIN(0),
    ADVERTISER_COMMON(1),
    ADVERTISER_BRAN(2),
    INFLUENCER(3),
    VISITOR(4)
}