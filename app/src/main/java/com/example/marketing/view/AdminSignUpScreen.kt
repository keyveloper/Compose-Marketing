package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.item.SignUpFormItem
import com.example.marketing.ui.component.SignUpForm
import com.example.marketing.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSignUpScreen(
    viewModel: AdminViewModel = hiltViewModel(),
    navController: NavController
) {
    val newLoginId: String
    val newPassword: String


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White,

            topBar = {
                // 상단 AppBar
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White // TopAppBar 배경색을 흰색으로 지정
                    ),
                    title = { Text(
                        text = "Admin Register...",
                        style = TextStyle(fontSize = 15.sp)
                    ) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            val adjustedTopPadding = (innerPadding.calculateTopPadding() - 16.dp).coerceAtLeast(0.dp)
            val adjustedPadding = PaddingValues(
                top = adjustedTopPadding,
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = innerPadding.calculateBottomPadding()
            )
            // 회원가입 폼 내용
            Box(modifier = Modifier.padding(adjustedPadding)) {
                SignUpForm(
                    title = "Welcome My World!",
                    toNext = {},
                    items = listOf(
                        SignUpFormItem(
                            label = "로그인 ID",
                            onValueChange = {},
                            placeholder = "이름을 입력해 주세요",
                            value = ""
                        ),
                        SignUpFormItem(
                            label = "비밀번호",
                            onValueChange = {},
                            placeholder = "비밀번호 조합: ",
                            value = ""
                        )
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminSignUpScreenPreview() {

}