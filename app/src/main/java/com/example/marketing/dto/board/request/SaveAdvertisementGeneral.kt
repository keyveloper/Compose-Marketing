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
    val draftId: Long
) {
    companion object {
        fun of(
            commonFields: AdvertisementCommonFields
        ): SaveAdvertisementGeneral = SaveAdvertisementGeneral(
            title = commonFields.title,
            reviewType = commonFields.reviewType,
            channelType = commonFields.channelType,
            itemName = commonFields.itemName,
            itemInfo = commonFields.itemInfo,
            recruitmentNumber = commonFields.recruitmentNumber,
            recruitmentStartAt = commonFields.recruitmentStartAt,
            recruitmentEndAt = commonFields.recruitmentEndAt,
            announcementAt = commonFields.announcementAt,
            reviewStartAt = commonFields.reviewStartAt,
            reviewEndAt = commonFields.reviewEndAt,
            endAt = commonFields.endAt,
            siteUrl = commonFields.siteUrl,
            draftId = commonFields.draftId
        )
    }
}