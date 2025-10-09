package com.example.fairthread.data.repository

import com.example.fairthread.data.model.CartItem
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Create user profile after registration
    suspend fun createUserProfile(uid: String, email: String) {
        val profile = mapOf(
            "email" to email,
            "createdAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users")
            .document(uid)
            .set(profile)
            .await()
    }

    // Save user settings
    suspend fun saveUserSettings(
        uid: String,
        theme: String,
        notificationsEnabled: Boolean
    ) {
        val settings = mapOf(
            "theme" to theme,
            "notificationsEnabled" to notificationsEnabled,
            "updatedAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users")
            .document(uid)
            .collection("settings")
            .document("profile")
            .set(settings)
            .await()
    }

    // Add item to user's cart
    suspend fun addItemToCart(
        uid: String,
        itemId: String,
        name: String,
        price: Double,
        quantity: Int
    ) {
        val cartItem = mapOf(
            "name" to name,
            "price" to price,
            "quantity" to quantity,
            "addedAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users")
            .document(uid)
            .collection("cart")
            .document(itemId)
            .set(cartItem)
            .await()
    }

    // Place an order
    suspend fun placeOrder(
        uid: String,
        orderId: String,
        items: List<Map<String, Any>>,
        total: Double
    ) {
        val order = mapOf(
            "items" to items,
            "total" to total,
            "status" to "pending",
            "orderedAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users")
            .document(uid)
            .collection("orders")
            .document(orderId)
            .set(order)
            .await()
    }

    suspend fun submitOrder(uid: String, items: List<CartItem>) {
        val orderData = mapOf(
            "items" to items.map {
                mapOf("name" to it.name, "price" to it.price, "quantity" to it.quantity)
            },
            "timestamp" to FieldValue.serverTimestamp()
        )
        firestore.collection("orders").document(uid).collection("history").add(orderData).await()
    }



    // Fetch cart items
    suspend fun getCartItems(uid: String): List<CartItem> {
        val snapshot = firestore.collection("cart").document(uid).collection("items").get().await()
        return snapshot.documents.mapNotNull {
            val data = it.data ?: return@mapNotNull null
            CartItem(
                id = it.id,
                name = data["name"]?.toString() ?: "",
                price = data["price"]?.toString()?.toDoubleOrNull() ?: 0.0,
                quantity = data["quantity"]?.toString()?.toIntOrNull() ?: 1
            )
        }
    }

    suspend fun removeCartItem(uid: String, itemId: String) {
        firestore.collection("cart").document(uid).collection("items").document(itemId).delete().await()
    }

    suspend fun updateCartItemQuantity(uid: String, itemId: String, quantity: Int) {
        firestore.collection("cart").document(uid).collection("items").document(itemId)
            .update("quantity", quantity).await()
    }

    suspend fun clearCart(uid: String) {
        val cartRef = firestore.collection("cart").document(uid).collection("items")
        val snapshot = cartRef.get().await()
        snapshot.documents.forEach { it.reference.delete().await() }
    }

    // Fetch orders
    suspend fun getOrders(uid: String): List<Map<String, Any>> {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("orders")
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.data }
    }

    // Fetch user settings
    suspend fun getUserSettings(uid: String): Map<String, Any>? {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("settings")
            .document("profile")
            .get()
            .await()
        return snapshot.data
    }

    suspend fun updateUserSettings(uid: String, settings: Map<String, Any>) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .update(settings)
            .await()
    }

}