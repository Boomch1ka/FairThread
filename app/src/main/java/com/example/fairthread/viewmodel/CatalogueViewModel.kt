package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogueViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    fun loadCategories() {
        _categories.value = listOf("Nike", "Shoes", "Stock")
    }
}