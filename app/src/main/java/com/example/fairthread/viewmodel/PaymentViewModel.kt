package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    enum class Status {
        IDLE,
        PROCESSING,
        SUCCESS,
        ERROR
    }

    private val _paymentStatus = MutableStateFlow(Status.IDLE)
    val paymentStatus: StateFlow<Status> = _paymentStatus

    fun processPayment(uid: String) {
        viewModelScope.launch {
            _paymentStatus.value = Status.PROCESSING
            try {
                // Simulate network delay for a better user experience
                delay(2000)

                // Get current cart items before creating an order
                val cartItems = repo.getCartItems(uid)

                if (cartItems.isNotEmpty()) {
                    // Submit the order with the items from the cart
                    repo.submitOrder(uid, cartItems)
                    // Clear the cart after the order is placed
                    repo.clearCart(uid)
                    _paymentStatus.value = Status.SUCCESS
                } else {
                    // If the cart is empty, there's nothing to order
                    _paymentStatus.value = Status.ERROR
                }
            } catch (e: Exception) {
                _paymentStatus.value = Status.ERROR
            }
        }
    }

    fun reset() {
        _paymentStatus.value = Status.IDLE
    }
}
