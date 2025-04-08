package com.example.marketing.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose .runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.google.gson.annotations.Until

@Composable
fun AuthHomeScreen(
    toInfluencer : () -> Unit = {},
    toAdvertiser : () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 54.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { toInfluencer() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray
                )
            ) {
                Text("인플루언서로 시작하기")
            }

            OutlinedButton(
                onClick = { toAdvertiser() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray
                )
            ) {
                Text("광고주로 시작하기")
            }

        }
    }
}
