package com.example.fairthread.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val uid: String,
    val username: String,
    val email: String,
    val password: String
)