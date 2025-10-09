package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.model.CartItem
import com.example.fairthread.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun loadCart(uid: String) {
        viewModelScope.launch {
            _cartItems.value = repo.getCartItems(uid)
        }
    }

    fun removeItem(uid: String, itemId: String) {
        viewModelScope.launch {
            repo.removeCartItem(uid, itemId)
            loadCart(uid)
        }
    }

    fun updateQuantity(uid: String, itemId: String, quantity: Int) {
        viewModelScope.launch {
            repo.updateCartItemQuantity(uid, itemId, quantity)
            loadCart(uid)
        }
    }
}