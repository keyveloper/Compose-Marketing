package com.example.marketing.dto.user.request

import com.example.marketing.domain.Admin

data class MakeNewAdminRequest(
    val loginId: String,

    val password: String
) {
    companion object {
        fun of(requestModel: SignUpAdmin): MakeNewAdminRequest {
            return MakeNewAdminRequest(
                loginId = requestModel.loginId,
                password = requestModel.password
            )
        }
    }
}