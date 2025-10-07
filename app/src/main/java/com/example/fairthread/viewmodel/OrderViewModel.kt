package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val orders: StateFlow<List<Map<String, Any>>> = _orders

    fun placeOrder(uid: String, orderId: String, items: List<Map<String, Any>>, total: Double) {
        viewModelScope.launch {
            repo.placeOrder(uid, orderId, items, total)
            loadOrders(uid)
        }
    }

    fun loadOrders(uid: String) {
        viewModelScope.launch {
            _orders.value = repo.getOrders(uid)
        }
    }
}