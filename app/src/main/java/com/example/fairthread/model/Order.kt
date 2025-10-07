package com.example.fairthread.model

data class Order(
    val id: String,
    val date: String,
    val total: Double,
    val status: String // e.g. "Delivered", "Pending"
)
