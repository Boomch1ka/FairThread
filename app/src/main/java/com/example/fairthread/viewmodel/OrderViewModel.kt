package com.example.fairthread.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.CartItem
import com.example.fairthread.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.fairthread.data.local.LocalOrderRepository
import com.example.fairthread.data.local.OrderEntity
import com.example.fairthread.utils.NetworkUtils
import com.google.gson.Gson


class OrderViewModel(
    application: Application,
    private val repo: FirestoreRepository = FirestoreRepository()
) : AndroidViewModel(application) {

    private val localRepo = LocalOrderRepository(application)
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun loadOrders(uid: String) {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val isOnline = NetworkUtils.isOnline(context)

            if (isOnline) {
                try {
                    val remoteOrders = repo.getUserOrders(uid)
                    _orders.value = remoteOrders

                    localRepo.clearOrders()
                    remoteOrders.forEach { order ->
                        val entity = OrderEntity(
                            id = order.id,
                            itemsJson = Gson().toJson(order.items),
                            timestamp = order.timestamp
                        )
                        localRepo.saveOrder(entity)
                    }
                } catch (e: Exception) {
                    loadOrdersFromLocal()
                }
            } else {
                loadOrdersFromLocal()
            }
        }
    }

    private fun loadOrdersFromLocal() {
        viewModelScope.launch {
            val entities = localRepo.getOrders()
            val orders = entities.map {
                Order(
                    id = it.id,
                    items = Gson().fromJson(it.itemsJson, Array<CartItem>::class.java).toList(),
                    timestamp = it.timestamp
                )
            }
            _orders.value = orders
        }
    }

    fun placeOrder(uid: String, items: List<CartItem>) {
        viewModelScope.launch {
            repo.submitOrder(uid, items)
            loadOrders(uid) // refresh orders after placing
        }
    }

}