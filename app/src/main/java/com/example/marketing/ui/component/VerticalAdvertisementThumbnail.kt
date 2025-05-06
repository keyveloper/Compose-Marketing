package com.example.marketing.ui.component

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.*
import coil3.compose.AsyncImage
import com.example.marketing.R
import com.example.marketing.domain.AdvertisementPackage
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.ReviewIcon
import com.example.marketing.enums.ReviewType
import kotlinx.coroutines.flow.MutableStateFlow

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
            AsyncImage(
                model = item.imageBytes,
                contentDescription = "Advertisement Image added",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

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

