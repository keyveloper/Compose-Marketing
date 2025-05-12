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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marketing.domain.DugKeywordCandidate
import com.example.marketing.enums.ApiCallStatus
import com.example.marketing.ui.color.LightGreen
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
                    .background(
                        color = LightGreen,
                        shape = CircleShape
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(LightGreen),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                text = "Top Blogger",
                style = MaterialTheme.typography.headlineLarge
            )

            AnimatedVisibility(
                visible = topBlogVisitCallStatus == ApiCallStatus.SUCCESS &&
                topBlogVisitStat.isNotEmpty(),
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                val pageCount = Int.MAX_VALUE
                val middle    = pageCount / 2
                // Shift so that middle maps to index 0
                val startPage = remember(topBlogVisitStat.size) {
                    middle - (middle % topBlogVisitStat.size)
                }
                val pagerState = rememberPagerState(
                    initialPage = startPage,
                    pageCount = { topBlogVisitStat.size }
                )

                VerticalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clipToBounds()
                ) { page ->
                    val index = ((page - startPage) % topBlogVisitStat.size
                            + topBlogVisitStat.size) % topBlogVisitStat.size

                    val item = topBlogVisitStat[index]

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .background(
                                color = Color.Gray,
                                shape = CircleShape
                            )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .fillMaxHeight(0.3f),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(65.dp)
                                        .background(Color.Gray, shape = CircleShape),
                                )

                                Text("@${item.bloggerId}")
                            }


                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .fillMaxHeight(0.3f),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(item.title)
                                Text(item.blogUrl)
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .fillMaxHeight(0.3f),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("방문자 수")
                                Text("5일 평균: ${item.avg5dVisitCount}")
                                Text("5일 최대: ${item.max5dVisitCount}")
                                Text("5일 최소: ${item.min5dVisitCount}")
                            }
                        }
                    }
                }
            }
        }
    }
}