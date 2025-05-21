package com.example.marketing.state

import com.example.marketing.domain.FollowFeedItem

sealed interface FollowingFeedFetchState {
    object Loading: FollowingFeedFetchState
    data class Ready(
        val feedItems: List<FollowFeedItem>
    ): FollowingFeedFetchState
    data class Error(val msg: String): FollowingFeedFetchState
}