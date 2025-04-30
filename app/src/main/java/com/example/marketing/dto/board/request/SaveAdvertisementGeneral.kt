package com.example.marketing.dto.board.request

import com.example.marketing.enums.ChannelType
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
) {
    companion object {
        fun of(
            title: String,
            reviewType: ReviewType,
            channelType: ChannelType,
            itemName: String,
            itemInfo: String?,
            recruitmentNumber: Int,
            recruitmentStartAt: Long,
            recruitmentEndAt: Long,
            announcementAt: Long,
            reviewStartAt: Long,
            reviewEndAt: Long,
            endAt: Long,
            siteUrl: String?
        ): SaveAdvertisementGeneral = SaveAdvertisementGeneral(
            title = title,
            reviewType = reviewType,
            channelType = channelType,
            itemName = itemName,
            itemInfo = itemInfo,
            recruitmentNumber = recruitmentNumber,
            recruitmentStartAt = recruitmentStartAt,
            recruitmentEndAt = recruitmentEndAt,
            announcementAt = announcementAt,
            reviewStartAt = reviewStartAt,
            reviewEndAt = reviewEndAt,
            endAt = endAt,
            siteUrl = siteUrl
        )
    }
}