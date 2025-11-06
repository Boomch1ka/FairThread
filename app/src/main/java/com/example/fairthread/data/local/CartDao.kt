package com.example.fairthread.data.local

import androidx.room.*
import com.example.fairthread.model.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    suspend fun getAll(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CartItem>)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}