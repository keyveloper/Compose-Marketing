package com.example.marketing.ui.item

import androidx.compose.ui.geometry.Offset
import com.example.marketing.enums.ChannelIcon

data class ChannelFloatingObject(
    val id: Int,
    var defaultPosition: Offset,
    var currentPosition: Offset,
    val channelUrl: String,
    val channelName: String,
    val icon: ChannelIcon
)