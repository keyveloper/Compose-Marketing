package com.example.marketing.ui.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.*
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

@Composable
fun VerticalAdvertisementThumbnail(
    item: AdvertisementThumbnailItem,
    modifier: Modifier = Modifier,
    onClick: (AdvertisementThumbnailItem) -> Unit
) {
    val reviewIconVector = ReviewIcon.fromCode(item.reviewType.code)!!.iconVector
    Column(
        modifier = modifier
            .clickable { onClick(item) }
    ) {

        // Image tmp -> should change to coil
        Card(
            modifier = Modifier
                .size(width = 190.dp, height = 180.dp),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            AsyncImage(
                model = item.imageBytes,
                contentDescription = "Advertisement Image added",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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

