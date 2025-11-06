package com.example.fairthread.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.local.LocalCartRepository
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.CartItem
import com.example.fairthread.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    application: Application,
    private val repo: FirestoreRepository = FirestoreRepository()
) : AndroidViewModel(application) {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    private val localRepo = LocalCartRepository(application)

    fun loadCart(uid: String) {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val isOnline = NetworkUtils.isOnline(context)

            if (isOnline) {
                try {
                    val remoteItems = repo.getCartItems(uid)
                    _cartItems.value = remoteItems
                    localRepo.clearCart()
                    localRepo.addItems(remoteItems)
                } catch (e: Exception) {
                    _cartItems.value = localRepo.getCartItems()
                }
            } else {
                _cartItems.value = localRepo.getCartItems()
            }
        }
    }


    fun removeItem(uid: String, itemId: String) {
        viewModelScope.launch {
            val currentItem = _cartItems.value.find { it.id == itemId }
            repo.removeCartItem(uid, itemId)
            currentItem?.let { localRepo.removeItem(it) }
            loadCart(uid)
        }
    }

    fun updateQuantity(uid: String, itemId: String, quantity: Int) {
        viewModelScope.launch {
            repo.updateCartItemQuantity(uid, itemId, quantity)
            val updatedItem = _cartItems.value.find { it.id == itemId }?.copy(quantity = quantity)
            updatedItem?.let { localRepo.addItem(it) }
            loadCart(uid)
        }
    }

    fun clearCart(uid: String) {
        viewModelScope.launch {
            repo.clearCart(uid)
            localRepo.clearCart()
            _cartItems.value = emptyList()
        }
    }
}