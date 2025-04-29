package com.example.marketing.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marketing.dao.JwtTokenDao
import com.example.marketing.entity.JwtTokenEntity

@Database(entities = [JwtTokenEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val jwtTokenDao: JwtTokenDao
}