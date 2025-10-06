package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StoresViewModel : ViewModel() {
    private val _stores = MutableStateFlow<List<String>>(emptyList())
    val stores: StateFlow<List<String>> = _stores

    fun loadStores() {
        _stores.value = listOf("Nike Store", "Adidas Outlet", "FairThread Merch")
    }
}