package com.example.marketing.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.enums.ChannelIcon
import com.example.marketing.enums.ChannelType
import com.example.marketing.enums.InfluencerSignUpPart
import com.example.marketing.enums.ScreenRoute
import com.example.marketing.ui.component.SignUpChannelCard
import com.example.marketing.ui.item.ChannelFloatingObject
import com.example.marketing.viewmodel.InfluencerSignUpViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfluencerSignUpScreen(
    viewModel: InfluencerSignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    // ------------✍️ input value -------------
    val loginId = viewModel.loginId.collectAsState()
    val password = viewModel.password.collectAsState()
    val birthday = viewModel.formattedBirthday.collectAsState()
    val channelCards = viewModel.channelCards.collectAsState()

    // ------------🥃 local state --------------
    val passwordValidation = viewModel.passwordValidation.collectAsState()
    val passwordValidationStatus =
        viewModel.passwordValidationStatus.collectAsState()

    // ------------🔃 status ------------------
    val part by viewModel.part.collectAsState()
    val signUpApiCallStatus by viewModel.signUpApiCallStatus.collectAsState()

    // ----------- 🚀 from server value -----------
    val coroutineScope = rememberCoroutineScope()


    // ------------ ⛏️ library ------------------
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = null,
        initialDisplayedMonthMillis = null,
        yearRange = 1900..2025,
    )

    // ------------ 🎮 controller  ---------------
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(millis))

            viewModel.updateFormattedDate(formatted)
        }
    }

    LaunchedEffect(signUpApiCallStatus) {
        if (signUpApiCallStatus == ApiCallStatus.SUCCESS) {
            navController.navigate(ScreenRoute.AUTH_INFLUENCER_LOGIN.route) {
                popUpTo(ScreenRoute.AUTH_INFLUENCER_SIGNUP.route) {
                    inclusive = true
                }
            }
        }
    }

    BackHandler {
        when(part) {
            InfluencerSignUpPart.DETAIL -> {
                // detail -> credential
                viewModel.updatePart(InfluencerSignUpPart.CREDENTIAL)
            }
            InfluencerSignUpPart.CHANNEL -> {
                // channel -> detail
                viewModel.updatePart(InfluencerSignUpPart.DETAIL)
            } else -> {
                // Optional: handle exit or finish activity if on first step
                // None
            }
        }
    }

    // -------------- 🖼️ UI --------------------
    AnimatedVisibility(
        visible = part.equals(InfluencerSignUpPart.CREDENTIAL),
        exit = ExitTransition.None,
        enter = EnterTransition.None
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
                        viewModel.updateLoginId(it)
                    },
                    label = { Text("아이디") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                AnimatedVisibility(
                    visible = loginId.value.isNotEmpty(),
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
                            value = passwordValidation.value,
                            onValueChange = { viewModel.updatePasswordValidation(it) },
                            label = { Text("비밀번호 확인") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Button(
                            onClick = { viewModel.updatePart(InfluencerSignUpPart.DETAIL) },
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
                        ) {
                            if (!viewModel.mathPasswords()) {
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

    }

    AnimatedVisibility(
        visible = part.equals(InfluencerSignUpPart.DETAIL),
        exit = ExitTransition.None,
        enter = EnterTransition.None
    ) {
        val showDataPicker = remember { MutableStateFlow(false) }
        val isPickerVisible by showDataPicker.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        append("안녕하세요! 👋\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(loginId.value)
                        }
                        append("님!")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    buildAnnotatedString {
                        append("회원가입 절차가 곧 끝나요!.\n")
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // text : data picker
                    OutlinedTextField(
                        value = birthday.value,
                        onValueChange = {
                        },
                        label = { Text("생년월일") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .weight(3.5f),
                        shape = RoundedCornerShape(8.dp),
                        enabled = false
                    )

                    Button(
                        onClick = { showDataPicker.value = true }, //data picker
                        modifier = Modifier
                            .height(64.dp)
                            .weight(1.5f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0f4c81), // classic blue
                            contentColor = Color.White
                        )
                    ) {
                        Text("날짜 선택")
                    }


                    if (isPickerVisible) {
                        Popup(
                            onDismissRequest = { showDataPicker.value = false },
                            alignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(600.dp)
                                    .shadow(8.dp, RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                            ) {

                                DatePicker(
                                    state = datePickerState,
                                    showModeToggle = false
                                )
                            }
                        }

                        BackHandler {
                            showDataPicker.value = false
                        }
                    }
                }

                AnimatedVisibility(
                    visible = birthday.value.isNotEmpty()
                ) {
                    Button(
                        onClick = { viewModel.updatePart(InfluencerSignUpPart.CHANNEL) },
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
                }
            }
        }
    }

    // channel
    AnimatedVisibility(
        visible = part.equals(InfluencerSignUpPart.CHANNEL),
        exit = ExitTransition.None,
        enter = EnterTransition.None
    ) {
        val addMode = remember { mutableStateOf(false) }

        if (addMode.value) {
            BackHandler {
                addMode.value = false
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.White)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 82.dp,
                    top = 65.dp
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (addMode.value) {
                        addMode.value = false
                    }
                }
        ) {
            FloatingActionButton(
                onClick = { coroutineScope.launch {
                    viewModel.signUp()
                } },
                containerColor = Color.Transparent,       // transparent background
                contentColor = Color.Gray,               // icon/text color
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)              // 👈 place it at top end (or TopCenter if you want)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = "signUp icon button",
                    modifier = Modifier.size(24.dp)
                )
            }

            FloatingActionButton(
                onClick = {
                    if (addMode.value) {
                        addMode.value = false
                    } else {
                        addMode.value = true
                    }
                },
                shape = CircleShape,
                containerColor = Color(0xFF0f4c81), // classic blue
                contentColor = Color.White,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.BottomCenter)
                    .zIndex(2f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Channel",
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "어떤 채널을 운영 중이신가요?",
                    style = MaterialTheme.typography.headlineSmall
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(channelCards.value) { channel ->
                        val animatedOffset by animateOffsetAsState(
                            targetValue = channel.currentPosition,
                            animationSpec = if (channel.currentPosition != channel.defaultPosition)
                                spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                            else
                                tween(durationMillis = 400, easing = FastOutSlowInEasing),
                            label = "CardOffset"
                        )
                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(animatedOffset.x.toInt(),
                                        animatedOffset.y.toInt())
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDrag = { change, dragAmount ->
                                            viewModel.updateChannelCardPosition(
                                                id = channel.id,
                                                offset = channel.currentPosition +
                                                        Offset(dragAmount.x, dragAmount.y)
                                            )
                                        },
                                        onDragEnd = {
                                            viewModel
                                                .resetChannelCardToDefaultPosition(id = channel.id)
                                        }
                                    )
                                }
                        ) {
                            SignUpChannelCard(
                                item = channel,
                                onDelete = { viewModel.deleteChannel(channel) }
                            )
                        }

                    }
                }


            }

            if (addMode.value) {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    val primaryId = viewModel.primaryId.collectAsState()
                    var expanded by remember { mutableStateOf(false) }
                    val options = listOf(
                        ChannelIcon.BLOGGER,
                        ChannelIcon.INSTAGRAM,
                        ChannelIcon.YOUTUBER,
                        ChannelIcon.THREAD
                    )
                    var selectedChannel by remember {
                        mutableStateOf(ChannelIcon.BLOGGER)
                    }
                    var channelUrl by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier.padding(16.dp) ,
                        verticalArrangement = Arrangement.spacedBy(16.dp)// ✅ Apply padding to the whole card content
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Toggle button
                            OutlinedButton(
                                onClick = { expanded = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(selectedChannel.painterId),
                                    contentDescription = selectedChannel.description,
                                    modifier = Modifier.size(28.dp),
                                    tint = Color.Unspecified
                                )
                            }
                            OutlinedTextField(
                                value = channelUrl,
                                onValueChange = {
                                    channelUrl = it
                                },
                                label = { Text("URL") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                                    .weight(3.5f),
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )

                        }
                        Button(
                            onClick = {
                                val ySpacing = 40f
                                val lastY =
                                    channelCards.value.lastOrNull()?.defaultPosition?.y ?: 0f
                                val newPosition = Offset(0f, lastY + ySpacing)

                                viewModel.addChannel(
                                    ChannelFloatingObject(
                                        id = primaryId.value + 1,
                                        channelName = "",
                                        channelUrl = channelUrl,
                                        icon = selectedChannel,
                                        defaultPosition = newPosition,
                                        currentPosition = newPosition,
                                        channelType = ChannelType.fromCode(selectedChannel.code)!!
                                        // 📌 not null always
                                    )
                                )
                                addMode.value = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0f4c81), // classic blue
                                contentColor = Color.White
                            ),
                            enabled = channelUrl.isNotEmpty()
                        ) {
                            Text("채널 추가하기")
                        }
                    }


                    // Dropdown
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { channel ->
                            DropdownMenuItem(
                                text = { Text(channel.title) },
                                onClick = {
                                    selectedChannel = channel
                                    expanded = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(channel.painterId),
                                        contentDescription = channel.description,
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
