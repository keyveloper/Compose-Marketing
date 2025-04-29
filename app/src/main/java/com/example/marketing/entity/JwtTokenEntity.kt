package com.example.marketing.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jwt_token")
data class JwtTokenEntity(
    @PrimaryKey val id: Int = 0, // always a single row,
    val token: String, // jwt token
)
