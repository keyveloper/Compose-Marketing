package com.example.marketing.dto.functions.response

data class GetFollowingAdvertiserInfosResponse(
    val frontErrorCode: Int,
    val errorMessage: String,
    val infos: List<FollowingAdvertiserInfo>
)