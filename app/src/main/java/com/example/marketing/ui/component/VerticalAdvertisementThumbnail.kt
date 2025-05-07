package com.example.marketing.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ReviewIcon

@Composable
fun VerticalAdvertisementThumbnail(
    item: AdvertisementThumbnailItem,
    modifier: Modifier = Modifier,
    onClick: (AdvertisementThumbnailItem) -> Unit,
    onToggleFavorite: (Long) -> Unit,
) {
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(190.dp)
            .clickable {
                onClick(item)
            }
    ) {
        IconButton(
            onClick = {
                if (isFavorite) {
                    isFavorite = false
                } else isFavorite = true
                onToggleFavorite(item.advertisementId)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)     // place at top-end of the Box
                .padding(4.dp)               // inset from edges
                // .offset(x = (-8).dp, y = 8.dp) // or fine-tune with offset
                .size(24.dp)                 // small icon size
                .zIndex(6f),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.6f),  // semi-opaque bg
                contentColor = if (isFavorite) Color.Red else Color.Gray
            )
        ) {
            Icon(
                imageVector = if (isFavorite)
                    Icons.Default.Favorite
                else
                    Icons.Default.FavoriteBorder,
                contentDescription = "Favorite toggle"
            )
        }
        Column {
            // Image tmp -> should change to coil
            if (item.unifiedCode != null) {
                val url = "http://192.168.223.89:8080/open/advertisement/image/${item.unifiedCode}"
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Advertisement Image",
                    modifier       = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale   = ContentScale.Crop
                )
            } else {
                // Show a Compose Icon instead
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector     = Icons.Default.Photo,  // or your own ImageVector
                        contentDescription = "Default Advertisement Icon",
                        modifier        = Modifier.size(48.dp),
                        tint            = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(13.dp))

            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // item name
            Text(
                text = item.itemName,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Channel
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val channelIcon = ChannelIcon.fromCode(item.channelType.code)
                Icon(
                    painter = painterResource(id = channelIcon!!.painterId),
                    contentDescription = channelIcon.description,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = channelIcon.title,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // review type
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val reviewIcon = ReviewIcon.fromCode(item.reviewType.code)
                Icon(
                    imageVector = reviewIcon!!.iconVector,
                    contentDescription = reviewIcon.description,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = reviewIcon.title,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

