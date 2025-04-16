package com.example.marketing.view

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.AdvertiserSignUpStatus
import com.example.marketing.enums.UserType
import com.example.marketing.viewmodel.AdvertiserSignUpViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AdvertiserSignUpScreen(
    viewModel: AdvertiserSignUpViewModel = hiltViewModel(),
) {
    // state - user info, service info, service map(locations)
    val liveStatus = viewModel.liveStatus.collectAsState()

    LaunchedEffect(Unit) {
        snapshotFlow { liveStatus.value }
            .collectLatest { status ->
                Log.i("advertiser-signup", "status: ${status}")
            }

    }
    // back handler
    BackHandler {
        when (liveStatus.value) {
            AdvertiserSignUpStatus.SERVICE_INFO -> {
                viewModel.updateLiveStatus(AdvertiserSignUpStatus.CREDENTIAL)
            }

            AdvertiserSignUpStatus.MAP -> {
                viewModel.updateLiveStatus(AdvertiserSignUpStatus.SERVICE_INFO)
            }

            else -> {
                // back to login screen
            }
        }
    }

    AnimatedVisibility(
        visible = liveStatus.value == AdvertiserSignUpStatus.CREDENTIAL,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        // get loginId, password,
        val loginId = viewModel.loginId.collectAsState()
        val password = viewModel.password.collectAsState()
        var passwordDoubleCheck by remember { mutableStateOf("") }
        var passwordValidation by remember { mutableStateOf(false) }
        var loginIdFilled by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            snapshotFlow { loginId.value }
                .collectLatest {
                    loginIdFilled = loginId.value.isNotBlank()
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = loginId.value,
                    onValueChange = {
                        viewModel.updateLoginId(it)
                    },
                    label = { Text("아이디") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                AnimatedVisibility(
                    visible = loginIdFilled,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { viewModel.updatePassword(it) },
                            label = { Text("비밀번호") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        OutlinedTextField(
                            value = passwordDoubleCheck,
                            onValueChange = { passwordDoubleCheck = it },
                            label = { Text("비밀번호 확인") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Button(
                            onClick = {
                                viewModel.updateLiveStatus(
                                    AdvertiserSignUpStatus.SERVICE_INFO
                                )
                            }, // api call !!
                            enabled = passwordValidation,
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

                        if (password.value.isNotEmpty()) {
                            if (password.value != passwordDoubleCheck) {
                                Text(
                                    text = "비밀번호가 일치하지 않습니다.",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                passwordValidation = false
                            } else {
                                Text(
                                    text = "비밀번호가 일치합니다.",
                                    color = Color.Blue,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                passwordValidation = true
                            }
                        }

                    }
                }
            }

        }
    }

    // service type, service nmme,
    AnimatedVisibility(
        visible = liveStatus.value == AdvertiserSignUpStatus.SERVICE_INFO,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        val companyName = viewModel.companyName.collectAsState()
        val homepageUrl = viewModel.homepageUrl.collectAsState()
        val userType = viewModel.userType.collectAsState()
        var nextStage by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 24.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                ,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🤔 어떤 사업을 하고 계신가요?"
                )
                // select advertiser type
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                viewModel.updateUserType(UserType.ADVERTISER_COMMON)
                            },
                        border = if (userType.value == UserType.ADVERTISER_COMMON)
                            BorderStroke(2.dp, Color(0xFF0f4c81)) else null
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text ="🏪",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "소상공인",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                viewModel.updateUserType(UserType.ADVERTISER_BRAND)
                            },
                        border = if (userType.value == UserType.ADVERTISER_BRAND)
                            BorderStroke(2.dp, Color(0xFF0f4c81)) else null
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text ="🏢",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "기업회원",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = companyName.value,
                    onValueChange = {
                        viewModel.updateCompanyName(it)
                    },
                    label = { Text("회사이름") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = homepageUrl.value ?: "",
                    onValueChange = {
                        viewModel.updateHomepageUrl(it)
                    },
                    label = { Text("홈페이지(선책)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                Button(
                    onClick = {
                        viewModel.updateLiveStatus(AdvertiserSignUpStatus.MAP)
                    }, // api call !!
                    enabled = nextStage,
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

                if (companyName.value.isNotEmpty()) {
                    nextStage = true
                }
            }
        }
    }

    // map
    AnimatedVisibility(
        visible = liveStatus.value == AdvertiserSignUpStatus.MAP,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        val context = LocalContext.current
        var query by remember { mutableStateOf("") }
        var selectedLocation by remember {
            mutableStateOf(LatLng(37.5665, 126.9780))
        }
        var mapVisible by remember { mutableStateOf(false) }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(selectedLocation, 10f) // zoom level 10
        }

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = 36.dp, start = 24.dp, end = 24.dp)
                .background(Color.White)
        ) {
            Text("👉 사업 주소지를 입력해주세요.")

            IconButton(onClick = { mapVisible = true }) {
                Text(text = "🗺️", fontSize = 72.sp)
            }
            AnimatedVisibility (
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(600.dp),
                visible = mapVisible,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Search location") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    GoogleMap(
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = rememberMarkerState(position = selectedLocation),
                            title = "Seoul",
                            snippet = "Capital of South Korea"
                        )
                    }
                }
            }
        }
    }
}