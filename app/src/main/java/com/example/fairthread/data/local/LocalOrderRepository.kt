package com.example.fairthread.data.local

import android.content.Context
import androidx.room.Room

class LocalOrderRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        FairThreadDatabase::class.java,
        "fairthread-db"
    ).build()

    private val dao = db.orderDao()

    suspend fun getOrders(): List<OrderEntity> = dao.getAll()
    suspend fun saveOrder(order: OrderEntity) = dao.insert(order)
    suspend fun clearOrders() = dao.clear()
}