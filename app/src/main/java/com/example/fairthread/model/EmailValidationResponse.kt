package com.example.fairthread.model

data class EmailValidationResponse(
    val email: String,
    val is_valid_format: Boolean,
    val is_disposable: Boolean,
    val is_deliverable: Boolean,
    val quality_score: String
)
