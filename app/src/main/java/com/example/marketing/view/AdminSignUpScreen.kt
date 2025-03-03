package com.example.marketing.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.item.SignUpFormItem
import com.example.marketing.ui.component.SignUpForm
import com.example.marketing.viewmodel.AdminViewModel

@Composable
fun AdminSignUpScreen(
    viewModel: AdminViewModel = hiltViewModel(),
    navController: NavController
) {
    val newLoginId: String
    val newPassword: String

    Surface(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        color = Color.White
    ) {
        SignUpForm(
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

@Preview(showBackground = true)
@Composable
fun AdminSignUpScreenPreview() {

}