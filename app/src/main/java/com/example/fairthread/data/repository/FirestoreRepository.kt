package com.example.fairthread.data.repository

import com.example.fairthread.model.CartItem
import com.example.fairthread.model.Order
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.fairthread.model.Product
import com.example.fairthread.model.Store
import com.google.firebase.auth.FirebaseAuth

class FirestoreRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    fun getCurrentUserId(): String? = auth.currentUser?.uid

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

    suspend fun isUserRegistered(email: String): Boolean {
        val snapshot = firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .await()
        return !snapshot.isEmpty
    }

    suspend fun getStores(): List<Store> {
        val snapshot = firestore.collection("stores").get().await()
        return snapshot.documents.mapNotNull { doc ->
            val data = doc.data ?: return@mapNotNull null
            Store(
                id = doc.id,
                name = data["name"]?.toString() ?: "Unnamed Store",
                description = data["description"]?.toString() ?: ""
            )
        }
    }

    suspend fun getStoreById(storeId: String): Store? {
        val doc = firestore.collection("stores").document(storeId).get().await()
        val data = doc.data ?: return null
        return Store(
            id = doc.id,
            name = data["name"]?.toString() ?: "Unnamed Store",
            description = data["description"]?.toString() ?: ""
        )
    }

    suspend fun getProductsByStore(storeId: String): List<Product> {
        val snapshot = firestore.collection("products")
            .whereEqualTo("storeId", storeId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { doc ->
            val data = doc.data ?: return@mapNotNull null
            Product(
                id = doc.id,
                name = data["name"]?.toString() ?: "",
                price = data["price"]?.toString()?.toDoubleOrNull() ?: 0.0,
                category = data["category"]?.toString() ?: ""
            )
        }
    }

    suspend fun getProductsByStoreAndCategory(storeId: String, category: String): List<Product> {
        val snapshot = firestore.collection("products")
            .whereEqualTo("storeId", storeId)
            .whereEqualTo("category", category)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val data = doc.data ?: return@mapNotNull null
            Product(
                id = doc.id,
                name = data["name"]?.toString() ?: "",
                price = data["price"]?.toString()?.toDoubleOrNull() ?: 0.0,
                category = category,
                storeId = storeId
            )
        }
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

    // Add item to user's cart
    suspend fun addToCart(uid: String, product: com.example.fairthread.model.Product) {
        val cartItem = mapOf(
            "name" to product.name,
            "price" to product.price,
            "quantity" to 1
        )
        firestore.collection("cart").document(uid).collection("items").document(product.id).set(cartItem).await()
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

    //Orders
    suspend fun submitOrder(uid: String, items: List<CartItem>) {
        val orderData = mapOf(
            "items" to items.map {
                mapOf("name" to it.name, "price" to it.price, "quantity" to it.quantity)
            },
            "timestamp" to FieldValue.serverTimestamp()
        )
        firestore.collection("orders").document(uid).collection("history").add(orderData).await()
    }

    // Fetch orders
    suspend fun getUserOrders(uid: String): List<Order> {
        val snapshot = firestore.collection("orders").document(uid).collection("history").get().await()
        return snapshot.documents.mapNotNull { doc ->
            val data = doc.data ?: return@mapNotNull null
            val rawItems = data["items"]
            val items = if (rawItems is List<*>) {
                rawItems.mapNotNull { item ->
                    if (item is Map<*, *>) {
                        CartItem(
                            id = "", // optional: use UUID or skip
                            name = item["name"]?.toString() ?: "",
                            price = item["price"]?.toString()?.toDoubleOrNull() ?: 0.0,
                            quantity = item["quantity"]?.toString()?.toIntOrNull() ?: 1
                        )
                    } else null
                }
            } else emptyList()

            Order(
                id = doc.id,
                items = items,
                timestamp = data["timestamp"]?.toString() ?: ""
            )
        }
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

    // Fetch user settings
    suspend fun getUserSettings(uid: String): Map<String, Any> {
        val snapshot = firestore.collection("users").document(uid).get().await()
        return snapshot.data ?: emptyMap()
    }

    suspend fun updateUserSettings(uid: String, settings: Map<String, Any>) {
        FirebaseFirestore.getInstance().collection("users").document(uid).update(settings).await()
    }

}