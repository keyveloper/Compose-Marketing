package com.example.marketing.view

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.dto.user.request.SignUpAdmin
import com.example.marketing.ui.item.SignUpFormItem
import com.example.marketing.state.SignUpAdminState
import com.example.marketing.ui.component.SignUpForm
import com.example.marketing.viewmodel.AdminSignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSignUpScreen(
    adminViewModel: AdminSignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    val signUpState by adminViewModel.signUpState.collectAsState()
    val loginId by adminViewModel.loginId.collectAsState()
    val password by adminViewModel.password.collectAsState()

    LaunchedEffect(key1 = signUpState) {
        if (signUpState is SignUpAdminState.Success) {
            navController.navigate("auth-home") {
                // 기존 백스택을 제거하고 싶으면 popUpTo 등의 옵션 사용 가능
                popUpTo("adminSignUp") { inclusive = true }
            }
        }
    }

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
                    toSubmit = { adminViewModel.signUp(
                        SignUpAdmin(
                            loginId = loginId,
                            password = password
                        )
                    ) },
                    items = listOf(
                        SignUpFormItem(
                            label = "로그인 ID",
                            onValueChange = { adminViewModel.updatedLoginId(it) },
                            placeholder = "이름을 입력해 주세요",
                            value = loginId,
                            needSecret = false
                        ),
                        SignUpFormItem(
                            label = "비밀번호",
                            onValueChange = { adminViewModel.updatedPassword(it) },
                            placeholder = "비밀번호 조합: ",
                            value = password,
                            needSecret = true
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