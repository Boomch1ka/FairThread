package com.example.fairthread.data.local

import android.content.Context
import androidx.room.Room
import com.example.fairthread.model.CartItem

class LocalCartRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        FairThreadDatabase::class.java,
        "fairthread-db"
    ).build()

    private val dao = db.cartDao()

    suspend fun getCartItems(): List<CartItem> = dao.getAll()

    suspend fun addItem(item: CartItem) = dao.insert(item)

    suspend fun addItems(items: List<CartItem>) = dao.insertAll(items)

    suspend fun removeItem(item: CartItem) = dao.delete(item)

    suspend fun clearCart() = dao.clear()
}