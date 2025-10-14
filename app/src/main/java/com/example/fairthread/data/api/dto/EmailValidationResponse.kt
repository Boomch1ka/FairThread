package com.example.fairthread.data.api.dto

data class EmailValidationResponse(
    val email: String,
    val is_valid_format: FormatCheck,
    val is_smtp_valid: Boolean?,
    val is_disposable_email: Boolean?,
    val is_role_email: Boolean?,
    val quality_score: String?
)

data class FormatCheck(
    val value: Boolean
)