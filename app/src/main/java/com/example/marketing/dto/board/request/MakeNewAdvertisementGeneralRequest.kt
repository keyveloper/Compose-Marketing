package com.example.marketing.dto.board.request

import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.ReviewType

data class MakeNewAdvertisementGeneralRequest(
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
            requestModel: SaveAdvertisementGeneral
        ): MakeNewAdvertisementGeneralRequest {
            return MakeNewAdvertisementGeneralRequest(
                title = requestModel.title,
                reviewType = requestModel.reviewType,
                channelType = requestModel.channelType,
                itemName = requestModel.itemName,
                itemInfo = requestModel.itemInfo,
                recruitmentNumber = requestModel.recruitmentNumber,
                recruitmentStartAt = requestModel.recruitmentStartAt,
                recruitmentEndAt = requestModel.recruitmentEndAt,
                announcementAt = requestModel.announcementAt,
                reviewStartAt = requestModel.reviewStartAt,
                reviewEndAt = requestModel.reviewEndAt,
                endAt = requestModel.endAt,
                siteUrl = requestModel.siteUrl,
            )
        }
    }
}
