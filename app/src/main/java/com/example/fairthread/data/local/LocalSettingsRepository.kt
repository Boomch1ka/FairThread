package com.example.fairthread.data.local

import android.content.Context
import androidx.room.Room

class LocalSettingsRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        FairThreadDatabase::class.java,
        "fairthread-db"
    ).build()

    private val dao = db.settingsDao()

    suspend fun getSettings(uid: String): SettingsEntity? = dao.getSettings(uid)
    suspend fun saveSettings(entity: SettingsEntity) = dao.saveSettings(entity)
    suspend fun deleteSettings(uid: String) = dao.deleteSettings(uid)
}