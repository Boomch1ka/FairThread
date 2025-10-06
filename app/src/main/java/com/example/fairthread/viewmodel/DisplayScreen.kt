package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DisplayViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items

    fun loadDisplayItems() {
        _items.value = listOf("Nike", "Shoes", "Info")
    }
}