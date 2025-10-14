package com.example.fairthread.data.api

import com.example.fairthread.data.api.dto.EmailValidationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EmailValidationApi {
    @GET("v1/")
    suspend fun validateEmail(
        @Query("api_key") apiKey: String,
        @Query("email") email: String
    ): EmailValidationResponse
}