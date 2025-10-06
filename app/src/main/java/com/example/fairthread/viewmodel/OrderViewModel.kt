package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<String>>(emptyList())
    val orders: StateFlow<List<String>> = _orders

    fun loadOrders() {
        _orders.value = listOf("Order #1001", "Order #1002") // Replace with API later
    }
}