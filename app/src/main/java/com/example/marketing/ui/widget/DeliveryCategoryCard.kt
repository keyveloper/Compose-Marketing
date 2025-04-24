package com.example.marketing.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.*
import androidx.compose .runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.example.marketing.enums.DeliveryCategoryIcon
import com.example.marketing.ui.color.*

@Composable
fun DeliveryCategoryCard(
    icon: DeliveryCategoryIcon,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(67.5.dp)
            .height(90.dp)
            .background(PastelPeach)
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 6.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Salmon, shape = CircleShape)
                    .border(2.dp, Color.LightGray, CircleShape)
                    .padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = icon.iconVector,
                    contentDescription = icon.contentDescription,
                )
            }

            Text(
                text = icon.toKorean,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}