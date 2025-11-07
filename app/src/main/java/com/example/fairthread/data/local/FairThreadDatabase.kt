package com.example.fairthread.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fairthread.model.CartItem

@Database(entities = [CartItem::class, OrderEntity::class, SettingsEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class FairThreadDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun settingsDao(): SettingsDao

}