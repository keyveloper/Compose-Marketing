package com.example.marketing.dto.user.request

import com.example.marketing.enums.ChannelType
import com.example.marketing.ui.item.ChannelFloatingObject

data class SignUpInfluencer(
    val loginId: String,
    val password: String,
    val birthday: String,
    val blogUrl: String?,
    val instagramUrl: String?,
    val threadUrl: String?,
    val youtuberUrl: String?,
) {
    companion object {
        fun of(
            loginId: String,
            password: String,
            birthday: String,
            cardObjects: List<ChannelFloatingObject>
        ): SignUpInfluencer {
            val blogUrl = cardObjects
                .firstOrNull { it.channelType == ChannelType.BLOGGER }?.channelUrl
            val instagramUrl = cardObjects
                .firstOrNull { it.channelType == ChannelType.INSTAGRAM }?.channelUrl
            val threadUrl = cardObjects
                .firstOrNull { it.channelType == ChannelType.THREAD }?.channelUrl
            val youtuberUrl = cardObjects
                .firstOrNull { it.channelType == ChannelType.YOUTUBER }?.channelUrl
            return SignUpInfluencer(
                loginId = loginId,
                password = password,
                birthday = birthday,
                blogUrl = blogUrl,
                instagramUrl = instagramUrl,
                threadUrl = threadUrl,
                youtuberUrl = youtuberUrl
            )
        }
    }
}