package com.example.fairthread.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface SettingsDao {
    @Query("SELECT * FROM settings WHERE uid = :uid LIMIT 1")
    suspend fun getSettings(uid: String): SettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: SettingsEntity)

    @Query("DELETE FROM settings WHERE uid = :uid")
    suspend fun deleteSettings(uid: String)
}
