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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.enums.UserStatus
import com.example.marketing.viewmodel.AdvertiserLoginViewModel

@Composable
fun AdvertiserLoginScreen(
    viewmodel: AdvertiserLoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val loginId = viewmodel.loginId.collectAsState()
    val password = viewmodel.password.collectAsState()
    val apiCallStatus = viewmodel.apiCallStatus.collectAsState()
    val advertiserId = viewmodel.advertiserId.collectAsState()

    LaunchedEffect(apiCallStatus.value) {
        if (apiCallStatus.value == ApiCallStatus.SUCCESS && advertiserId.value != -1L) {
            navController.navigate(
                "${ScreenRoute.MAIN_INIT.route}/" +
                        "${UserStatus.ADVERTISER.ordinal}/" +
                        "${advertiserId.value}"
            )
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
                value = loginId.value,
                onValueChange = { viewmodel.updateLoginId(it) },
                label = { Text("아이디") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { viewmodel.updatePassword(it) },
                label = { Text("비밀번호") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = { viewmodel.login() }, // make dto
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0f4c81), // classic blue
                    contentColor = Color.White
                )
            ) {
                Text("Login")
            }

            Text(
                text ="회원이 아니신가요?",
                modifier = Modifier.clickable {
                    navController.navigate(ScreenRoute.AUTH_ADVERTISER_SIGNUP.route)
                }
            )
        }
    }
}
