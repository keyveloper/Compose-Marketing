package com.example.marketing.ui.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.*
import coil3.compose.AsyncImage
import com.example.marketing.enum.ChannelIcon
import com.example.marketing.enum.ChannelType
import com.example.marketing.enum.ReviewIcon
import com.example.marketing.enum.ReviewType

@Composable
fun AdvertisementThumbnail(
    item: AdvertisementThumbnailItem,
    onClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick(item.advertisementId) }
    ) {

        // Image
        AsyncImage(
            model = item.thumbnailImageUrl,
            contentDescription ="${item.itemName}'s thumbnail image",
            modifier = Modifier
                .size(width = 170.dp, height = 180.dp)
                .clip(shape = RoundedCornerShape(16.dp))
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
                painter = painterResource(id = channelIcon!!.iconRes),
                contentDescription = channelIcon.description
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
                imageVector = reviewIcon!!.icon,
                contentDescription = reviewIcon.description,
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = reviewIcon.title,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAdvertisementThumbnail() {
    AdvertisementThumbnail(
        item =  AdvertisementThumbnailItem(
            advertisementId = 1L,
            thumbnailImageUrl = "https://via.placeholder.com/170x180.png",
            title = "Special Discount",
            itemName = "Product X",
            channelType = ChannelType.BLOGGER,
            reviewType = ReviewType.VISITED
        ),
        onClick = {}
    )
}