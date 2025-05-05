package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserType
import com.example.marketing.viewmodel.InfluencerLoginViewModel
import kotlinx.coroutines.launch

@Composable
fun InfluencerLoginScreen(
    viewModel: InfluencerLoginViewModel = hiltViewModel(),
    navController: NavController
) {
    // ------------✍️ input value -------------
    val loginId by viewModel.loginId.collectAsState()
    val password by viewModel.password.collectAsState()

    // ------------🔃 status ---------------
    val loginApiComposable by viewModel.loginApiCallStatus.collectAsState()

    // ----------- 🚀 from server value -----------
    val coroutineScope = rememberCoroutineScope()
    val influencerId by viewModel.influencerId.collectAsState()

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(loginApiComposable) {
        if (loginApiComposable == ApiCallStatus.SUCCESS) {
            navController.navigate(
                "${ScreenRoute.MAIN_INIT.route}/" +
                        "${UserType.INFLUENCER}/" +
                        "${influencerId}"
            ) {
                popUpTo(ScreenRoute.AUTH_INFLUENCER_LOGIN.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = loginId ?: "",
                onValueChange = { viewModel.updateLoginId(it) },
                label = { Text("아이디") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
            )

            OutlinedTextField(
                value = password ?: "",
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("비밀번호") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.login()
                    }
                }, // make dto
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0f4c81), // classic blue
                    contentColor = Color.White
                ),
                enabled = viewModel.checkApiAllowed()
            ) {
                Text("Login")
            }

            Text(
                text ="💃 인플루언서로 가입하기",
                modifier = Modifier.clickable {
                    navController.navigate(ScreenRoute.AUTH_INFLUENCER_SIGNUP.route)
                }
            )
        }
    }
}