package com.example.fairthread.data.repository

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

    // Fetch cart items
    suspend fun getCartItems(uid: String): List<Map<String, Any>> {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("cart")
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.data }
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
}