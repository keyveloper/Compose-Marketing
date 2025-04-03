package com.example.marketing.ui.component.bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
) {
    Box( // 이건 MainScrren에서 선언한 속성 그대로 적용
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), // Size를 그대로 적용하려면 이렇게
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back Icon"
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search Icon"
                )
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Noti Icon"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainTopBar() {
    MainTopBar(
        modifier = Modifier.fillMaxWidth()
            .height(56.dp)

    )
}