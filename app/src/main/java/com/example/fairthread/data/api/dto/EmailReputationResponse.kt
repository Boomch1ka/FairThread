package com.example.fairthread.data.api.dto

data class EmailReputationResponse(
    val email_address: String?,
    val email_deliverability: EmailDeliverability?,
    val email_quality: EmailQuality?
)

data class EmailDeliverability(
    val is_format_valid: Boolean?,
    val is_smtp_valid: Boolean?
)

data class EmailQuality(
    val score: String?,
    val is_disposable: Boolean?,
    val is_role: Boolean?
)