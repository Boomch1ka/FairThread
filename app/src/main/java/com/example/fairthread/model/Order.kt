package com.example.fairthread.model

data class Order(
    val id: String,
    val items: List<CartItem>,
    val timestamp: String,
    //val status: String // e.g. "Delivered", "Pending"
)
