package com.example.marketing.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marketing.entity.JwtTokenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JwtTokenDao {
    @Query("SELECT * FROM jwt_token WHERE id = 0")
    fun observeToken(): Flow<JwtTokenEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(token: JwtTokenEntity)

    @Query("DELETE FROM jwt_token WHERE id = 0")
    suspend fun clear()
}