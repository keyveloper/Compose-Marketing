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
import com.example.marketing.viewmodel.AdminViewModel

@Composable
fun AdminSignUpScreen(
    viewModel: AdminViewModel = hiltViewModel(),
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

    }
}

@Preview(showBackground = true)
@Composable
fun AdminSignUpScreenPreview() {
    AdminSignUpScreen()
}