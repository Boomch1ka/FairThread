package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fairthread.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addItem(item: CartItem) {
        _cartItems.value = _cartItems.value + item
    }

    fun removeItem(itemId: String) {
        _cartItems.value = _cartItems.value.filterNot { it.id == itemId }
    }

    fun updateQuantity(itemId: String, quantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == itemId) it.copy(quantity = quantity) else it
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}