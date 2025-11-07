package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    private val _orderPlaced = MutableStateFlow(false)
    val orderPlaced: StateFlow<Boolean> = _orderPlaced

    fun loadCart(uid: String) {
        viewModelScope.launch {
            val items = repo.getCartItems(uid)
            _cartItems.value = items
            _total.value = items.sumOf { it.price * it.quantity }
        }
    }

    fun placeOrder(uid: String) {
        viewModelScope.launch {
            repo.submitOrder(uid, _cartItems.value)
            repo.clearCart(uid)
            _orderPlaced.value = true
        }
    }
}
