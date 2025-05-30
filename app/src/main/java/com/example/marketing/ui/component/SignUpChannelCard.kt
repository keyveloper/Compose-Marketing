package com.example.marketing.ui.component


import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marketing.ui.item.ChannelFloatingObject

@Composable
fun SignUpChannelCard(
    item: ChannelFloatingObject,
    modifier: Modifier = Modifier,
    onDelete: (ChannelFloatingObject) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon section
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                // Replace with appropriate image loading
                Icon(
                    painter = painterResource(id = item.icon.painterId),
                    contentDescription = "Channel Icon",
                    modifier = Modifier.size(28.dp),
                    tint = Color.Unspecified
                )
            }

            // Divider between icon and text
            HorizontalDivider(
                color = Color.LightGray,
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )

            // Channel name section
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item.channelUrl,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Divider between icon and text
            HorizontalDivider(
                color = Color.LightGray,
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )

            IconButton(
                onClick = { onDelete(item) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "channel icon delete"
                )
            }
        }
    }
}
