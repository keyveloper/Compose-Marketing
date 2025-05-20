package com.example.marketing.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.domain.DugKeywordCandidate
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.ui.color.ClassicBlue
import com.example.marketing.ui.color.LightGreen
import com.example.marketing.ui.color.SkyWashBlue
import com.example.marketing.viewmodel.GoldenKeywordDetailViewModel

@Composable
fun GoldenKeywordDetailScreen(
    viewModel: GoldenKeywordDetailViewModel = hiltViewModel(),
    targetKeywordStat: DugKeywordCandidate
) {
    // ------------✍️ input value -------------
    // ------------🔃 status ------------ '
    val topBlogVisitCallStatus by viewModel.topBlogVisitCallStatus.collectAsState()
    // ----------- 🚀 api value -----------
    val keywordStat by viewModel.targetKeywordStat.collectAsState()
    val topBlogVisitStat by viewModel.topBlogVisitStat.collectAsState()
    val pcQueryCount = keywordStat?.monthlySearchVolumePc ?: 0
    val mobileQueryCount = keywordStat?.monthlySearchVolumeMobile ?: 0
    val total = pcQueryCount + mobileQueryCount

    // ----------- 🔨 utils -------------

    // ----------- 🔭 Launched Effect -------------
    LaunchedEffect(Unit) {
        viewModel.updateTargetKeywordStat(targetKeywordStat)
        viewModel.fetchTopBlogVisitStat() // suspend!
    }

    // ----------- 🖼️ UI -----------------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column( // stat view
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "${keywordStat?.keyword ?: ""} 분석",
                style = MaterialTheme.typography.headlineLarge
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(
                        width = 2.dp,
                        color = ClassicBlue,           // your desired border color
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Query Count",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.25f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Color.Gray, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Computer,
                                    contentDescription = "monthly pc query Volumn",
                                    tint = Color.White
                                )
                            }
                            Text(
                                text ="${keywordStat?.monthlySearchVolumePc}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "PC",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.25f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Color.Gray, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhoneIphone,
                                    contentDescription = "monthly query mobile",
                                    tint = Color.White
                                )
                            }
                            Text(
                                text ="${keywordStat?.monthlySearchVolumeMobile}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Mobile",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.4f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Color.Gray, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "monthly query total",
                                    tint = Color.White
                                )
                            }

                            Text(
                                text ="$total",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "total",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }

            // blog lists
            Text(
                text = "🥇 Top Blogger",
                style = MaterialTheme.typography.headlineLarge
            )

            AnimatedVisibility(
                visible = topBlogVisitCallStatus == ApiCallStatus.SUCCESS &&
                topBlogVisitStat.isNotEmpty(),
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                // Shift so that middle maps to index 0
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { topBlogVisitStat.size }
                )

                VerticalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .border(
                            width = 2.dp,
                            color = SkyWashBlue
                        )
                        .height(450.dp)
                ) { page ->
                    val size  = topBlogVisitStat.size
                    val index = ((page % size) + size) % size  // wrap into [0..size-1]
                    val item  = topBlogVisitStat[index]

                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(16.dp))

                        Text("@${item.bloggerId}")

                        Spacer(Modifier.height(16.dp))

                        Text(item.title)

                        Spacer(Modifier.height(16.dp))

                        Text("방문자 수")
                        Spacer(Modifier.height(6.dp))
                        Text("5일 평균: ${item.avg5dVisitCount}")
                        Text("5일 최대: ${item.max5dVisitCount}")
                        Text("5일 최소: ${item.min5dVisitCount}")
                    }
                }
            }
        }
    }
}