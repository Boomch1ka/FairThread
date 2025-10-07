package com.example.fairthread.data.api.dto

data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val storeId: String
)
