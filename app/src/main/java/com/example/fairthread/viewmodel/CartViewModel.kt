package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<String>>(emptyList())
    val cartItems: StateFlow<List<String>> = _cartItems

    fun addItem(item: String) {
        _cartItems.value = _cartItems.value + item
    }

    fun removeItem(item: String) {
        _cartItems.value = _cartItems.value - item
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}