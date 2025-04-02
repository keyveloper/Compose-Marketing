package com.example.marketing.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose .runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import com.example.marketing.viewmodel.InfluencerSignUpViewModel
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfluencerSignUpScreen(
    viewModel: InfluencerSignUpViewModel = hiltViewModel()
) {
    val loginId = viewModel.loginId.collectAsState()
    val loginIdFilled = viewModel.loginIdFilled.collectAsState()
    val credentialStatus = viewModel.credentialStatus.collectAsState()
    val password = viewModel.password.collectAsState()
    val passwordValidation = viewModel.passwordValidation.collectAsState()
    val passwordValidationStatus =
        viewModel.passwordValidationStatus.collectAsState()
    val date = viewModel.date.collectAsState()
    val datePickerLive = viewModel.datePickerLive.collectAsState()
    val datePickerState = rememberDatePickerState()
    val selectedMillis = datePickerState.selectedDateMillis


    // part
    val credentialPart = viewModel.credentialPartLive.collectAsState()
    val detailPart = viewModel.detailPartStatus.collectAsState()
    val channelPart = viewModel.channelPartStatus.collectAsState()



    LaunchedEffect(Unit) {
        snapshotFlow { loginId.value }
            .collectLatest { id ->
                viewModel.updateLoginFilled(id.isNotBlank())
            }
    }

    // credential part
    AnimatedVisibility(
        visible = credentialPart.value && !detailPart.value && !channelPart.value,
        exit = ExitTransition.None
    ) {
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
                    onValueChange = {
                        viewModel.setLoginId(it)

                    },
                    label = { Text("아이디") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                AnimatedVisibility(
                    visible = loginIdFilled.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { viewModel.setPassword(it) },
                            label = { Text("비밀번호") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        OutlinedTextField(
                            value = passwordValidation.value,
                            onValueChange = { viewModel.setPasswordValidation(it) },
                            label = { Text("비밀번호 확인") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Button(
                            onClick = { viewModel.nextToDetailPart() }, // api call !!
                            enabled = passwordValidationStatus.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0f4c81), // classic blue
                                contentColor = Color.White
                            )
                        ) {
                            Text("Next")
                        }

                        if (password.value.isNotEmpty() &&
                            passwordValidation.value.isNotEmpty()
                        )
                            if (password.value != passwordValidation.value) {
                                Text(
                                    text = "비밀번호가 일치하지 않습니다.",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                viewModel.updatePasswordValidationStatus(false)
                            } else {
                                Text(
                                    text = "비밀번호가 일치합니다.",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                viewModel.updatePasswordValidationStatus(true)
                            }
                    }
                }
            }
        }

    }

    //  details part
    LaunchedEffect(selectedMillis) {
        selectedMillis?.let {
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(it)) // ✅ 올바른 변환
            viewModel.updateDate(formattedDate)
        }
    }
    AnimatedVisibility(
        visible = detailPart.value
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("??")
                Log.i("singup", "${detailPart.value}, ${credentialPart.value}, ${channelPart.value}")
            }


            if (datePickerLive.value) {
                Popup(
                    onDismissRequest = {
                        viewModel.updateDatePickerLive(false)
                    },
                    alignment = Alignment.TopStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 64.dp)
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false
                        )
                    }
                }
            }
        }
    }

    // channel
    AnimatedVisibility(
        visible = channelPart.value
    ) { }
}