package com.example.marketing.state

import com.example.marketing.domain.AdParticipatedByInfluencer

interface InfluencerParticipatingAdInitState {
    object Loading: InfluencerParticipatingAdInitState
    data class Ready(
        val adParticipatedByInfluencer: List<AdParticipatedByInfluencer>
    ): InfluencerParticipatingAdInitState
    data class Error(val msg: String): InfluencerParticipatingAdInitState
}