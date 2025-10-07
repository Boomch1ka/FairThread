package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val cartItems: StateFlow<List<Map<String, Any>>> = _cartItems

    fun loadCart(uid: String) {
        viewModelScope.launch {
            val items = repo.getCartItems(uid)
            _cartItems.value = items
        }
    }

    fun addToCart(uid: String, itemId: String, name: String, price: Double, quantity: Int) {
        viewModelScope.launch {
            repo.addItemToCart(uid, itemId, name, price, quantity)
            loadCart(uid) // Refresh after adding
        }
    }
}