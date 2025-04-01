package com.example.marketing.ui.widget

import android.graphics.drawable.Icon
import android.media.metrics.Event
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.marketing.enums.EventStatus

@Composable
fun CategoryBox(
    modifier: Modifier = Modifier,
    emoji: String?,
    icon: @Composable () -> Unit,
    onCategorySelected: (EventStatus) -> Unit,
    eventCode: Int,
    ) {
    Box(
        modifier = modifier
            .size(32.dp)
            .border(
            width = 2.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                val status = EventStatus.fromCode(eventCode)

                status?.let {
                    onCategorySelected(status)
                }
            },
        contentAlignment = Alignment.Center

    ) {
        emoji?.let { Text(emoji) } ?: icon()
    }
}