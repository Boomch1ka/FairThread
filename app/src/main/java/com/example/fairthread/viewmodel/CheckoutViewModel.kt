package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CheckoutViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    private val _orderPlaced = MutableStateFlow<Result<Unit>?>(null)
    val orderPlaced: StateFlow<Result<Unit>?> = _orderPlaced

    fun calculateTotal(cartItems: List<Map<String, Any>>) {
        val sum = cartItems.sumOf {
            val price = it["price"] as? Double ?: 0.0
            val qty = it["quantity"] as? Int ?: 1
            price * qty
        }
        _total.value = sum
    }

    fun placeOrder(uid: String, cartItems: List<Map<String, Any>>) {
        val orderId = UUID.randomUUID().toString()
        val total = _total.value
        viewModelScope.launch {
            try {
                repo.placeOrder(uid, orderId, cartItems, total)
                _orderPlaced.value = Result.success(Unit)
            } catch (e: Exception) {
                _orderPlaced.value = Result.failure(e)
            }
        }
    }
}