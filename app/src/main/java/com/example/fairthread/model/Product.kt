package com.example.fairthread.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val category: String,
    val description: String = "",
    val imageUrl: String = "",
    val storeId: String = ""
)