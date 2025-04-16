package com.example.marketing.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.enums.GoldenKeywordScreenStatus
import com.example.marketing.ui.color.LemonChiffon
import com.example.marketing.ui.color.LightBlue
import com.example.marketing.ui.color.LightLime
import com.example.marketing.ui.color.Lumber
import com.example.marketing.ui.color.PaleMauve
import com.example.marketing.viewmodel.GoldenKeywordViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun GoldenKeywordScreen(
    modifier: Modifier = Modifier,
    viewModel: GoldenKeywordViewModel = hiltViewModel()
) {
    val searchKeyword = viewModel.searchKeyword.collectAsState()
    val screenStatus = viewModel.screenStatus.collectAsState()
    val fetchStatus = viewModel.fetchStatus.collectAsState()
    val goldenKeywords = viewModel.goldenKeywords.collectAsState()
    val selectedKeyword = viewModel.selectedGoldenKeyword.collectAsState()

    Box(
        modifier = modifier
            .background(LightLime)
            .padding(horizontal = 24.dp)
    ) {
        // IDLE
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = screenStatus.value == GoldenKeywordScreenStatus.IDLE,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 🔤 Animated text above input
                AnimatedContent(
                    targetState = searchKeyword.value,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(300,
                                delayMillis = 50)) togetherWith
                                fadeOut(animationSpec = tween(300))
                    },
                    label = "Animated Text"
                ) { keyword ->
                    if (keyword.isEmpty()) {
                        Text(
                            text = "🪙 황금 키워드를 찾아보세요!",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    } else {
                        Text(
                            text = "🔍 \"${keyword}\" 로 검색합니다",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                // 🧾 Input field
                OutlinedTextField(
                    value = searchKeyword.value,
                    onValueChange = { viewModel.updatedSearchKeyword(it) },
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(8.dp),
                    label = { Text("키워드 입력") },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.fetchGoldenKeywords() }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "fetch goldenKeyword icon button",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    singleLine = true
                )
            }
        }

        // FETCHED
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = screenStatus.value == GoldenKeywordScreenStatus.FETCHED,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            BackHandler {
                viewModel.updateScreenStatus(GoldenKeywordScreenStatus.IDLE)
            }
            val offsetX = remember { Animatable(0f) }
            val offsetY = remember { Animatable(0f) }
            val scope = rememberCoroutineScope()

            val infiniteTransition = rememberInfiniteTransition(label = "floatAnim")
            val floatOffsetY by infiniteTransition.animateFloat(
                initialValue = -4f,
                targetValue = 4f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "float"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            scope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount.x / 4)
                                offsetY.snapTo(offsetY.value + dragAmount.y / 4)
                                // simulate "shiver"
                                offsetX.animateTo(
                                    0f,
                                    animationSpec = spring(stiffness = Spring.StiffnessLow))
                                offsetY.animateTo(
                                    0f,
                                    animationSpec = spring(stiffness = Spring.StiffnessLow))
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                FlowRow(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = offsetX.value
                            translationY = offsetY.value + floatOffsetY
                        },
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    goldenKeywords.value.forEach { stat ->
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp
                        ) {
                            Text(
                                text = stat.keyword,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                                    .clickable {
                                        viewModel.updateScreenStatus(
                                            GoldenKeywordScreenStatus.VIEW_DETAIL)
                                        viewModel.updateSelectedKeyword(stat.keyword)
                                    },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }


        }

        // VIEW_DETAIL
        AnimatedVisibility(
            visible = screenStatus.value == GoldenKeywordScreenStatus.VIEW_DETAIL,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(LemonChiffon)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BackHandler {
                    viewModel.updateScreenStatus(GoldenKeywordScreenStatus.FETCHED)
                }
                Text(
                    text = "👉 ${selectedKeyword.value?.keyword}",
                    style = MaterialTheme.typography.titleLarge
                )

                // monthly volume
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Lumber)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "🗽 Search Volume",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Computer,
                                    contentDescription = "monthlySearchVolumePc Icon"
                                )
                                Text(text = "PC\n ${selectedKeyword.value?.monthlySearchVolumePc}")
                            }

                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Smartphone,
                                    contentDescription = "monthlySearchVolumeMobile Icon"
                                )
                                Text(text =
                                "PC\n ${selectedKeyword.value?.monthlySearchVolumeMobile}")
                            }
                        }
                    }
                }

                // stats
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(PaleMauve)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "MonthlyPostingVolume:" +
                                    "${selectedKeyword.value?.monthlyMeaningfulPostingVolume} ",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val tooltipState = rememberTooltipState()
                            Text(
                                text = "MKEI: ${selectedKeyword.value?.mKEI}",
                                style = MaterialTheme.typography.titleLarge
                            )
                            TooltipBox(
                                positionProvider = TooltipDefaults
                                    .rememberPlainTooltipPositionProvider(),
                                tooltip = {
                                    PlainTooltip {
                                        Text(
                                            "MKEI = (monthlySearchVolume)^2 / monthlyPostingVolume")
                                    }
                                },
                                state = tooltipState,
                                enableUserInput = true
                            ) {
                                IconButton(
                                    onClick = { /* Handle click if needed */ },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .hoverable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            enabled = true
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "MKEI is monthlySearch² / monthlyPosting"
                                    )
                                }
                            }
                        }
                    }
                }


                // top blog view

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(selectedKeyword.value!!.blogVisitStat) { blog ->
                        AnimatedContent(
                            targetState = true,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(durationMillis = 500)) with
                                        fadeOut(animationSpec = tween(durationMillis = 500))
                            },
                            contentAlignment = Alignment.CenterStart
                        ) { isVisible ->
                            if (isVisible) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .padding(horizontal = 16.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults
                                        .cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                buildAnnotatedString {
                                                    append(blog.title)
                                                    withLink(
                                                        LinkAnnotation.Url(
                                                            blog.blogUrl,
                                                            TextLinkStyles(
                                                                style = SpanStyle(LightBlue)
                                                            )
                                                        )
                                                    ) {}
                                                },
                                                softWrap = false,
                                                maxLines = 1,
                                                overflow = TextOverflow.Clip
                                            )

                                            Text(blog.avg5dVisitCount.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
