package com.example.marketing.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.domain.DugKeywordCandidate
import com.example.marketing.enums.GoldenKeywordFetchStatus
import com.example.marketing.enums.GoldenKeywordScreenStatus
import com.example.marketing.ui.component.FloatingKeywordCloud
import com.example.marketing.viewmodel.GoldenKeywordViewModel

@Composable
fun GoldenKeywordScreen(
    modifier: Modifier = Modifier,
    viewModel: GoldenKeywordViewModel = hiltViewModel()
) {
    // ------------✍️ input value -------------
    val keyword by viewModel.keyword.collectAsState()
    val context by viewModel.context.collectAsState()
    var selectedCandidate by remember { mutableStateOf<DugKeywordCandidate?> (null) }
    // ------------🔃 status ------------
    val fetchStatus by viewModel.fetchStatus.collectAsState()
    val screenStatus by viewModel.screenStatus.collectAsState()
    // ----------- 🚀 api value -----------
    val candidates by viewModel.candidates.collectAsState()
    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(selectedCandidate) {
        if (selectedCandidate != null) {
            viewModel.updateScreenStatus(GoldenKeywordScreenStatus.DETAIL)
        }
    }

    // ----------- 🎮 controller -----------
    BackHandler {
        when(fetchStatus) {
            GoldenKeywordFetchStatus.IDLE -> {

            }

            GoldenKeywordFetchStatus.SUCCESS -> {
                viewModel.updateFetchStatus(GoldenKeywordFetchStatus.IDLE)
                viewModel.setCandidates()
            }

            else -> {

            }
        }
    }


    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        // IDLE
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = screenStatus == GoldenKeywordScreenStatus.INIT,
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
                    targetState = keyword,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(
                                300,
                                delayMillis = 50
                            )
                        ) togetherWith
                                fadeOut(animationSpec = tween(300))
                    },
                    label = "Animated Text"
                ) { keyword ->
                    if (keyword == null) {
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

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = keyword ?: "",
                    onValueChange = { viewModel.updateKeyword(it) },
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(8.dp),
                    label = { Text("키워드 입력") },
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = context ?: "",
                    onValueChange = { viewModel.updateContext(it) },
                    modifier = Modifier
                        .height(128.dp)
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(8.dp),
                    label = { Text("문맥 입력") }
                )

                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(64.dp),
                    onClick = { 
                        viewModel.digCandidates()
                    },
                    shape = RoundedCornerShape(8.dp),
                    enabled = viewModel.canDig()
                ) {
                    Text("키워드 찾기")
                }
            }
        }

        // FETCHED
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = screenStatus == GoldenKeywordScreenStatus.CANDIDATES,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            FloatingKeywordCloud(
                candidatesInfo = candidates,
                onItemClick = { candidate -> selectedCandidate = candidate }
            )
        }

        // Detail
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = screenStatus == GoldenKeywordScreenStatus.DETAIL,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            GoldenKeywordDetailScreen(
                targetKeywordStat = selectedCandidate!!
            )
        }
    }
}
