package com.example.marketing.dto.board.request

import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.ReviewType

data class SaveAdvertisementGeneral(
    val title: String,

    val reviewType: ReviewType,

    val channelType: ChannelType,

    val itemName: String,

    val itemInfo: String?,

    val recruitmentNumber: Int,

    val recruitmentStartAt: Long,

    val recruitmentEndAt: Long,

    val announcementAt: Long,

    val reviewStartAt: Long,

    val reviewEndAt: Long,

    val endAt: Long,

    val siteUrl: String?,
)