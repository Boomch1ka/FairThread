package com.example.fairthread.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val itemsJson: String, // serialized list of CartItem
    val timestamp: String
)