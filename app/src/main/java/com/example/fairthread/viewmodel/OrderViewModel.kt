package com.example.fairthread.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.R
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.CartItem
import com.example.fairthread.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun loadOrders(uid: String) {
        viewModelScope.launch {
            _orders.value = repo.getUserOrders(uid)
        }
    }

    fun placeOrder(context: Context, uid: String, items: List<CartItem>) {
        viewModelScope.launch {
            repo.submitOrder(uid, items)
            loadOrders(uid) // refresh orders after placing
            showOrderConfirmationNotification(context)
        }
    }

    private fun showOrderConfirmationNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "order_confirmation_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Order Confirmation"
            val descriptionText = "Notifications for order confirmations"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with a more suitable icon
            .setContentTitle("Order Placed!")
            .setContentText("Your order has been successfully placed.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
