package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fairthread.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun loadOrders() {
        _orders.value = listOf(
            Order(id = "1001", date = "2025-10-01", total = 299.99, status = "Delivered"),
            Order(id = "1002", date = "2025-10-03", total = 149.50, status = "Pending")
        )
    }
}