package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.model.Category
import com.example.fairthread.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class CatalogueViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCatalogue()
    }

    fun loadCatalogue() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val categorySnapshot = firestore.collection("categories").get().await()
                val productSnapshot = firestore.collection("products").get().await()

                _categories.value = categorySnapshot.documents.mapNotNull { it.toObject(Category::class.java)?.copy(id = it.id) }
                _products.value = productSnapshot.documents.mapNotNull { it.toObject(Product::class.java)?.copy(id = it.id) }

                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val snapshot = firestore.collection("products")
                    .whereEqualTo("category", categoryId)
                    .get()
                    .await()

                _products.value = snapshot.documents.mapNotNull { it.toObject(Product::class.java)?.copy(id = it.id) }
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectCategory(categoryName: String) {
        // Find the category by name to get its ID
        val categoryId = _categories.value.find { it.name.equals(categoryName, ignoreCase = true) }?.id
        if (categoryId != null) {
            filterProductsByCategory(categoryId)
        } else {
            // Optionally, handle the case where the category name doesn't match any known ID
            _errorMessage.value = "Category not found: $categoryName"
        }
    }
}