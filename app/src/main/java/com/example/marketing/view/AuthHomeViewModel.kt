package com.example.marketing.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController

// user : influencer, advertiser, admin 
// select user signup/login

@Composable
fun AuthHomeViewModel(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .padding(18.dp)
            .background(Color.White)
            .fillMaxSize(),
    ) {
        LogoSection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 1/6
        )

        // (2) 메인 이미지(안내 문구) 섹션: 전체 높이의 4/6 = 2/3
        MainSection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f) // 4/6
        )

        // (3) 로그인 옵션 섹션: 전체 높이의 1/6
        LoginCards(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f) // 1/6,

        )
    }
}

@Composable
fun LogoSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("Logo here")
    }
}

@Composable
fun MainSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "main section here!"

        )
    }

}

@Composable
fun LoginCards(
    navController: NavController,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 알바님 카드
        OptionCard() {
            navController.navigate("auth-influencer")
        }

        Spacer(modifier = Modifier.height(5.dp))

        // 사장님 카드
        OptionCard() {
            navController.navigate("auth-advertiser")
        }
    }
}

@Composable
fun OptionCard(
    onClick : () -> Unit
)  {
    Card(
        modifier = Modifier
            .size(165.dp)
            .clickable { onClick() }, // 정사각형
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}